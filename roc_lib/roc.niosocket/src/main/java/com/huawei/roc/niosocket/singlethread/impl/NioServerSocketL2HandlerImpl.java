
package com.huawei.roc.niosocket.singlethread.impl;

import java.nio.channels.SelectionKey;

import com.huawei.roc.niosocket.singlethread.handler.ISocketL2Handler;

public class NioServerSocketL2HandlerImpl implements ISocketL2Handler {
    /**
     * 处理进入消息
     * @param key key
     */
    public void handleStart(SelectionKey key) {

    }

    /**
     * 处理退出消息
     * @param key key
     */
    public void handleFinish(SelectionKey key) {

    }

    /**
     * 处理异常消息
     * @param key key
     */
    public void handleException(SelectionKey key, Exception e) {

    }
}
