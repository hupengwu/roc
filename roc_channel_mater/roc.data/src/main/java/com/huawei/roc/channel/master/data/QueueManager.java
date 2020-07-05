/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.master.data;

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
    private static ConcurrentLinkedQueue<Map<String, Object>> southClientJsonRequests =
        new ConcurrentLinkedQueue<Map<String, Object>>();

    /**
     * 3.回应客户端请求
     */
    private static ConcurrentLinkedQueue<Map<String, Object>> southClientJsonResponds =
        new ConcurrentLinkedQueue<Map<String, Object>>();

    public static ConcurrentLinkedQueue<Map<String, Object>> getSouthClientJsonResponds() {
        return southClientJsonResponds;
    }

    public static ConcurrentLinkedQueue<Map<String, Object>> getSouthClientJsonRequests() {
        return southClientJsonRequests;
    }

    /**
     * 1.某个socket已经出现完整分组
     */
    private static ConcurrentLinkedQueue<SocketChannel> northHasGroupBuffs = new ConcurrentLinkedQueue<SocketChannel>();

    /**
     * 2.客户端的Json请求队列
     */
    private static ConcurrentLinkedQueue<Map<String, Object>> northClientJsonRequests =
        new ConcurrentLinkedQueue<Map<String, Object>>();

    /**
     * 3.回应客户端请求
     */
    private static ConcurrentLinkedQueue<Map<SocketChannel, String>> northClientJsonResponds =
        new ConcurrentLinkedQueue<Map<SocketChannel, String>>();

    public static ConcurrentLinkedQueue<Map<SocketChannel, String>> getNorthClientJsonResponds() {
        return northClientJsonResponds;
    }

    public static ConcurrentLinkedQueue<SocketChannel> getNorthHasGroupBuffs() {
        return northHasGroupBuffs;
    }

    public static ConcurrentLinkedQueue<Map<String, Object>> getNorthClientJsonRequests() {
        return northClientJsonRequests;
    }
}
