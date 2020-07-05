/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.node.north.server.task;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alibaba.fastjson.JSON;
import com.huawei.roc.channel.node.SocketManager;
import com.huawei.roc.channel.node.data.MapperManager;
import com.huawei.roc.channel.node.data.QueueManager;
import com.huawei.roc.channel.node.north.server.mapper.RequestQueryer4Status;
import com.huawei.roc.channel.node.vo.constant.Status;
import com.huawei.roc.channel.node.vo.server.ConnectVO;
import com.huawei.roc.channel.node.vo.server.RequestVO;
import com.huawei.roc.channel.node.vo.server.RespondVO;
import com.huawei.roc.frame.socket.task.SocketTask;
import com.huawei.roc.frame.utils.EscapeUtils;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.socket.NioServerSocket;
import com.huawei.roc.utils.IsNullOrEmptyUtil;

public class ServerTask {
    public static void makeGroupBuff2Json() {
        SocketTask socketTask = new SocketTask();
        socketTask.setJsonQueue(QueueManager.getServerClientJsonRequests());
        socketTask.setSocketMapper(MapperManager.getServerSocketContainer());

        // 1.将分组块转换成json字符串
    }

    public static void recvJson2Vo() {
        ConcurrentLinkedQueue<Map<String, Object>> prevQueue = QueueManager.getServerClientJsonRequests();
        ConcurrentLinkedQueue<Map<SocketChannel, String>> nextQueue = QueueManager.getServerClientJsonResponds();

        // 取出一个消息
        Map<String, Object> message = prevQueue.poll();
        if (message == null) {
            return;
        }

        try {
            String json = (String) message.get("json");
            SocketChannel channel = (SocketChannel) message.get("channel");
            if (json == null || channel == null) {
                return;
            }

            RequestVO reqVO = JSON.parseObject(json, RequestVO.class);
            if (reqVO.getConnect() == null) {
                reqVO.setConnect(new ConnectVO());
            }

            // 是否为新的请求
            RespondVO rsp = null;
            if (IsNullOrEmptyUtil.isNullOrEmpty(reqVO.getReceiptKey())) {
                rsp = createRequest(channel, reqVO);

                // 保存到客户端操作请求
                KeyValueMapper mapper = MapperManager.getRequestContainer();
                mapper.put(reqVO.getReceiptKey(), reqVO);

            } else {
                rsp = queryRequest(channel, reqVO);

                // 删除客户端操作请求
                KeyValueMapper mapper = MapperManager.getRequestContainer();
                if (rsp.getStatus().equals(Status.recved)) {
                    mapper.remove(reqVO.getReceiptKey());
                }
            }

            // 填入队列
            Map<SocketChannel, String> sc2jsn = new HashMap<SocketChannel, String>();
            sc2jsn.put(channel, JSON.toJSONString(rsp));
            nextQueue.offer(sc2jsn);

        } catch (Exception e) {
            e.toString();
        }
    }

    private static RespondVO createRequest(SocketChannel channel, RequestVO reqVO) {
        // 新的请求：分配回执UUID
        RespondVO rsp = new RespondVO();

        // 标识：收到请求的时间
        long time = System.currentTimeMillis();
        reqVO.getConnect().setRequestTime(time);

        // 标识：回执key
        String receiptKey = UUID.randomUUID().toString().replaceAll("-", "");
        reqVO.setReceiptKey(receiptKey);

        // 标识：socket channel
        reqVO.setChannel(channel);

        // 构造返回消息
        rsp.setReceiptKey(reqVO.getReceiptKey());
        rsp.setRequestKey(reqVO.getRequestKey());
        rsp.setClientId(reqVO.getClientId());
        rsp.setStatus(Status.accpeted);

        return rsp;
    }

    private static RespondVO queryRequest(SocketChannel channel, RequestVO reqVO) throws Exception {
        // 新的请求：分配回执UUID
        RespondVO rsp = new RespondVO();

        KeyValueMapper mapper = MapperManager.getRequestContainer();
        if (mapper.containsKey(reqVO.getReceiptKey())) {
            RequestQueryer4Status reader = new RequestQueryer4Status(reqVO.getReceiptKey());
            mapper.read(reader);
            rsp = reader.getRsp();

            rsp.setReceiptKey(reqVO.getReceiptKey());
            rsp.setRequestKey(reqVO.getRequestKey());
            rsp.setClientId(reqVO.getClientId());
        } else {
            // 构造返回消息
            rsp.setReceiptKey(reqVO.getReceiptKey());
            rsp.setRequestKey(reqVO.getRequestKey());
            rsp.setClientId(reqVO.getClientId());
            rsp.setStatus(Status.notexit);
        }

        return rsp;
    }

    public static void respondClient() {
        ConcurrentLinkedQueue<Map<SocketChannel, String>> prevQueue = QueueManager.getMasterClientJsonResponds();
        NioServerSocket serverSocket = SocketManager.getNorthServerSocket();

        // 取出一个消息
        Map<SocketChannel, String> message = prevQueue.poll();
        if (message == null) {
            return;
        }

        try {
            for (Entry<SocketChannel, String> entry : message.entrySet()) {
                SocketChannel channel = entry.getKey();
                String jsn = entry.getValue();

                // 编码成转义字符
                byte[] outs = EscapeUtils.escapeByteArray(jsn.getBytes());

                // 循环发送
                serverSocket.writeChannel(channel, outs);
            }
        } catch (Exception e) {
            e.toString();
        }
    }
}
