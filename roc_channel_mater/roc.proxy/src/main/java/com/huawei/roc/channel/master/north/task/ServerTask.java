/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.master.north.task;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.huawei.roc.channel.master.SocketManager;
import com.huawei.roc.channel.master.data.QueueManager;
import com.huawei.roc.frame.constant.Escape;
import com.huawei.roc.niosocket.multithread.socket.NioServerSocket;
import com.huawei.roc.utils.EscapeUtils;

public class ServerTask {

    public static void recvJson2Vo() {
        ConcurrentLinkedQueue<Map<String, Object>> prevQueue = QueueManager.getNorthClientJsonRequests();
        ConcurrentLinkedQueue<Map<SocketChannel, String>> nextQueue = QueueManager.getNorthClientJsonResponds();

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

        } catch (Exception e) {
            e.toString();
        }
    }

    public static void respondClient() {
        // ConcurrentLinkedQueue<Map<SocketChannel, String>> prevQueue = QueueManager.getSouthClientJsonResponds();
        // NioServerSocket serverSocket = SocketManager.getNorthSocket();
        //
        // // 取出一个消息
        // Map<SocketChannel, String> message = prevQueue.poll();
        // if (message == null) {
        // return;
        // }
        //
        // try {
        // for (Entry<SocketChannel, String> entry : message.entrySet()) {
        // SocketChannel channel = entry.getKey();
        // String jsn = entry.getValue();
        //
        // // 编码成转义字符
        // byte[] outs = EscapeUtils.escapeByteArray(jsn
        // .getBytes(), Escape.esc, Escape.begin, Escape.end, Escape.escEsc, Escape.escBegin, Escape.escEnd);
        //
        // // 循环发送
        // serverSocket.writeChannel(channel, outs);
        // }
        // } catch (Exception e) {
        // e.toString();
        // }
    }

}
