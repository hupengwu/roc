/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.node;

import com.huawei.roc.channel.node.data.MapperManager;
import com.huawei.roc.frame.socket.handler.SocketAccpetHandler;
import com.huawei.roc.frame.socket.handler.SocketCloseHandler;
import com.huawei.roc.frame.socket.handler.SocketReadHandler;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.handler.level1.SocketL1Handler;
import com.huawei.roc.niosocket.multithread.socket.NioServerSocket;

public class SocketManager {
    private static NioServerSocket northServerSocket = new NioServerSocket();

    private static NioServerSocket northMasterSocket = new NioServerSocket();

    public static NioServerSocket getNorthServerSocket() {
        return northServerSocket;
    }

    public static NioServerSocket getNorthMasterSocket() {
        return northMasterSocket;
    }

    public static void init() {
        try {
            northMasterSocket.setPort(10000);
            northMasterSocket.setReadThreadCount(1);
            SocketL1Handler masterHandler = (SocketL1Handler) northMasterSocket.getSocketHandler();
            KeyValueMapper masterSocketContainer = MapperManager.getMasterSocketContainer();
            masterHandler.setAccpetHandler(new SocketAccpetHandler(masterSocketContainer));
            masterHandler.setCloseHandler(new SocketCloseHandler(masterSocketContainer));
            masterHandler.setReadHandler(new SocketReadHandler(masterSocketContainer));
            northMasterSocket.start();

            northServerSocket.setPort(10001);
            northServerSocket.setReadThreadCount(5);
            SocketL1Handler northHandler = (SocketL1Handler) northServerSocket.getSocketHandler();
            KeyValueMapper serverSocketContainer = MapperManager.getServerSocketContainer();
            northHandler.setAccpetHandler(new SocketAccpetHandler(serverSocketContainer));
            northHandler.setCloseHandler(new SocketAccpetHandler(serverSocketContainer));
            northHandler.setReadHandler(new SocketAccpetHandler(serverSocketContainer));
            northServerSocket.start();
        } catch (Exception e) {
            e.toString();
        }
    }
}
