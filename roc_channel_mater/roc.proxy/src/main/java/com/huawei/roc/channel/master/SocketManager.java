/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.master;

import java.io.IOException;

import com.huawei.roc.channel.master.data.MapperManager;
import com.huawei.roc.channel.master.south.handler.MasterSocketCloseHandler;
import com.huawei.roc.channel.master.south.handler.MasterSocketConnectHandler;
import com.huawei.roc.channel.master.south.handler.MasterSocketReadHandler;
import com.huawei.roc.frame.socket.handler.SocketAccpetHandler;
import com.huawei.roc.frame.socket.handler.SocketCloseHandler;
import com.huawei.roc.frame.socket.handler.SocketReadHandler;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.handler.level1.SocketL1Handler;
import com.huawei.roc.niosocket.multithread.socket.NioClientSocket;
import com.huawei.roc.niosocket.multithread.socket.NioServerSocket;

public class SocketManager {
    private static NioServerSocket northSocket = new NioServerSocket();

    private static NioClientSocket southSocket = new NioClientSocket();

    public static NioClientSocket getSouthSocket() {
        return southSocket;
    }

    public static NioServerSocket getNorthSocket() {
        return northSocket;
    }

    public static void init() {
        try {
            initNorthSocket();
            initSouthSocket();
        } catch (Exception e) {
            e.toString();
        }
    }

    private static void initNorthSocket() throws IOException {
        northSocket.setPort(20001);
        northSocket.setReadThreadCount(1);
        SocketL1Handler northHandler = (SocketL1Handler) northSocket.getSocketHandler();
        KeyValueMapper socketChannelContainer = MapperManager.getNodeContainer();
        northHandler.setAccpetHandler(new SocketAccpetHandler(socketChannelContainer));
        northHandler.setCloseHandler(new SocketCloseHandler(socketChannelContainer));
        northHandler.setReadHandler(new SocketReadHandler(socketChannelContainer));
        northSocket.start();
    }

    private static void initSouthSocket() throws IOException {
        southSocket = new NioClientSocket();
        southSocket.setReadThreadCount(1);
        SocketL1Handler southHandler = (SocketL1Handler) southSocket.getSocketHandler();
        southHandler.setConnetHandler(new MasterSocketConnectHandler());
        southHandler.setCloseHandler(new MasterSocketCloseHandler());
        southHandler.setReadHandler(new MasterSocketReadHandler());
        southSocket.start();
    }
}
