
package com.huawei.roc.channel.master.south.task;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alibaba.fastjson.JSON;
import com.huawei.roc.channel.master.ProxyManager;
import com.huawei.roc.channel.master.SocketManager;
import com.huawei.roc.channel.master.data.MapperManager;
import com.huawei.roc.channel.master.data.QueueManager;
import com.huawei.roc.channel.master.south.mapper.NodeQueryer4NeedConnect;
import com.huawei.roc.channel.master.south.mapper.NodeUpdater4HasConnected;
import com.huawei.roc.channel.master.south.mapper.NodeUpdater4HasTryConnect;
import com.huawei.roc.frame.socket.task.SocketTask;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.socket.NioClientSocket;
import com.huawei.roc.restlike.RESTFulResponder;
import com.huawei.roc.restlike.RESTfulContextVO;

public class SouthTask {
    public static void makeGroupBuff2Json() {
        SocketTask socketTask = new SocketTask();
        socketTask.setJsonQueue(QueueManager.getSouthClientJsonResponds());
        socketTask.setSocketMapper(MapperManager.getSouthSocketContainer());

        // 1.将分组块转换成json字符串
        socketTask.makeGroupBuff2Json();
    }

    /**
     * 接收响应数据
     */
    public static void recvRespond() {
        ConcurrentLinkedQueue<Map<String, Object>> prevQueue = QueueManager.getSouthClientJsonResponds();

        while (!prevQueue.isEmpty()) {
            // 取出消息
            Map<String, Object> message = prevQueue.poll();
            if (message == null) {
                return;
            }

            try {
                SocketChannel channel = (SocketChannel) message.get("channel");
                String json = (String) message.get("json");

                // Json转换成结构化数据
                RESTfulContextVO respondVO = JSON.parseObject(json, RESTfulContextVO.class);

                RESTFulResponder responder =
                    ProxyManager.getProxy().getResponder(respondVO.getResource(), respondVO.getMethod());

                if (responder != null) {

                    String bodyParam = "";
                    if (respondVO.getParam() != null) {
                        bodyParam = respondVO.getParam().toString();
                    }

                    Map<String, Object> mapParam = new HashMap<String, Object>();
                    try {
                        mapParam = JSON.parseObject(respondVO.getParam().toString());
                    } catch (Exception e) {
                        e.toString();
                    }

                    try {
                        respondVO.setParam(responder.invokeMethod(channel, respondVO, bodyParam, mapParam));
                    } catch (Exception e) {
                        respondVO.setException(e.getMessage());
                    }
                } else {
                    respondVO.setException("not found this onRecver!");
                }
            } catch (Exception e) {
                e.toString();
            }
        }
    }

    /**
     * 尝试对未建立连接的节点建立连接
     */
    public static void tryConnect() {

        KeyValueMapper nodeContainer = MapperManager.getNodeContainer();
        NioClientSocket southSocket = SocketManager.getSouthSocket();

        try {
            // 查找尚未建立连接的节点
            Map<String, SocketAddress> nodes = new HashMap<String, SocketAddress>();
            nodeContainer.foreach(new NodeQueryer4NeedConnect(System.currentTimeMillis(), nodes));

            for (Entry<String, SocketAddress> entry : nodes.entrySet()) {
                String nodeId = entry.getKey();
                SocketAddress address = entry.getValue();

                try {
                    SocketChannel socketChannel = southSocket.connect(address);

                    // 连接成功：更新socket
                    nodeContainer
                        .update(new NodeUpdater4HasConnected(nodeId, System.currentTimeMillis(), socketChannel));
                } catch (Exception e) {
                    e.toString();

                    // 连接失败：更新本次连接时间
                    nodeContainer.update(new NodeUpdater4HasTryConnect(nodeId, System.currentTimeMillis()));
                    continue;
                }

            }
        } catch (Exception e) {
            e.toString();
        }

    }
}