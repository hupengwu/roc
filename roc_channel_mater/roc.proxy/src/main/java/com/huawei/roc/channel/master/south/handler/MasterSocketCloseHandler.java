
package com.huawei.roc.channel.master.south.handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.huawei.roc.channel.master.data.MapperManager;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.handler.ISocketL2Handler;

public class MasterSocketCloseHandler implements ISocketL2Handler {
    /**
     * 处理进入消息
     * 
     * @param key key
     */
    public void handle(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();

        KeyValueMapper socketChannelContainer = MapperManager.getSouthSocketContainer();
        if (socketChannelContainer.containsKey(sc)) {
            socketChannelContainer.remove(sc);
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