/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.node.data;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 消息队列
 * 
 * @author h00442047
 * @since 2019年12月20日
 */
public class QueueManager {
    /**
     * 2.客户端的Json请求队列
     */
    private static ConcurrentLinkedQueue<Map<String, Object>> serverClientJsonRequests =
        new ConcurrentLinkedQueue<Map<String, Object>>();

    /**
     * 3.回应客户端请求
     */
    private static ConcurrentLinkedQueue<Map<SocketChannel, String>> serverClientJsonResponds =
        new ConcurrentLinkedQueue<Map<SocketChannel, String>>();

    private static ConcurrentLinkedQueue<Map<String, Object>> masterClientJsonRequests =
        new ConcurrentLinkedQueue<Map<String, Object>>();

    private static ConcurrentLinkedQueue<Map<SocketChannel, String>> masterClientJsonResponds =
        new ConcurrentLinkedQueue<Map<SocketChannel, String>>();

    public static ConcurrentLinkedQueue<Map<SocketChannel, String>> getMasterClientJsonResponds() {
        return masterClientJsonResponds;
    }

    public static ConcurrentLinkedQueue<Map<String, Object>> getMasterClientJsonRequests() {
        return masterClientJsonRequests;
    }

    public static ConcurrentLinkedQueue<Map<SocketChannel, String>> getServerClientJsonResponds() {
        return serverClientJsonResponds;
    }

    public static ConcurrentLinkedQueue<Map<String, Object>> getServerClientJsonRequests() {
        return serverClientJsonRequests;
    }
}
