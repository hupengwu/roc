
package com.huawei.roc.frame.socket.handler;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.handler.ISocketL2Handler;

public class SocketCloseHandler implements ISocketL2Handler {
    private KeyValueMapper socketChannelContainer;

    public SocketCloseHandler(KeyValueMapper socketChannelContainer) {
        this.socketChannelContainer = socketChannelContainer;
    }

    /**
     * 处理进入消息
     * 
     * @param key key
     */
    public void handle(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();

        if (this.socketChannelContainer.containsKey(sc)) {
            this.socketChannelContainer.remove(sc);
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