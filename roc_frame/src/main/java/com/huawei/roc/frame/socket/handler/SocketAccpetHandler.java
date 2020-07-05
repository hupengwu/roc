/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.frame.socket.handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.handler.ISocketL2Handler;

/**
 * 默认的L2接口响应实现体：它是放在L1接口实现体里面的默认空操作，目的是保证SocketL1Handler有一个SocketL2Handler实例，不会出现空操作
 * 
 * @author h00442047
 * @since 2019年12月13日
 */
public class SocketAccpetHandler implements ISocketL2Handler {
    private KeyValueMapper socketChannelContainer;

    public SocketAccpetHandler(KeyValueMapper socketChannelContainer) {
        this.socketChannelContainer = socketChannelContainer;
    }

    /**
     * 处理进入消息
     * 
     * @param key key
     */
    public void handle(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();

        // 检查是否为新的连接
        this.socketChannelContainer.put(sc, new LinkedList<byte[]>());
    }

    /**
     * 处理异常消息
     * 
     * @param key key
     */
    public void handleException(SelectionKey key, Exception e) {
    }
}
