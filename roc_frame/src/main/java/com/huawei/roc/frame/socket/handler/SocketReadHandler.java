/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.frame.socket.handler;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.huawei.roc.frame.socket.mapper.SocketUpdater4AddBuff;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.handler.ISocketL2Handler;

/**
 * 接收数据的响应
 * 
 * @author h00442047
 * @since 2019年12月19日
 */
public class SocketReadHandler implements ISocketL2Handler {
    private KeyValueMapper keyValueMapper;

    public SocketReadHandler(KeyValueMapper keyValueMapper) {
        this.keyValueMapper = keyValueMapper;
    }

    /**
     * 处理进入消息：
     * 读线程中绑定的附件是ByteBuffer结构的对象，里面保存待提取的数据
     * 
     * @param key key
     */
    public void handle(SelectionKey key) {
        try {
            // 去除channel
            if (!SocketChannel.class.isInstance(key.channel())) {
                return;
            }
            SocketChannel channel = SocketChannel.class.cast(key.channel());

            // 取出附件:缓存
            if (!ByteBuffer.class.isInstance(key.attachment())) {
                return;
            }
            ByteBuffer buff = ByteBuffer.class.cast(key.attachment());

            // 处理方法1：将数据写入到一个byte数组
            int len = buff.limit() - buff.position();
            byte[] dst = new byte[len];
            buff.get(dst);

            // 将数据保存到缓存中
            keyValueMapper.update(new SocketUpdater4AddBuff(channel, dst));
        } catch (Exception e) {
            e.toString();
        }
    }

    /**
     * 处理异常消息
     * 
     * @param key key
     */
    public void handleException(SelectionKey key, Exception e) {
    }
}