
package com.huawei.roc.niosocket.multithread.demo;

import java.io.IOException;

import com.huawei.roc.niosocket.multithread.handler.level1.SocketL1Handler;
import com.huawei.roc.niosocket.multithread.handler.level2.SocketAccpetHandler;
import com.huawei.roc.niosocket.multithread.handler.level2.SocketCloseHandler;
import com.huawei.roc.niosocket.multithread.handler.level2.SocketReadHandler;
import com.huawei.roc.niosocket.multithread.handler.level2.SocketWriteHandler;
import com.huawei.roc.niosocket.multithread.socket.NioServerSocket;

public class DemoServer {
    public static void main(String[] args) throws IOException {
        NioServerSocket server = new NioServerSocket();
        server.setPort(10000);
        server.setReadThreadCount(10);

        SocketL1Handler handler = (SocketL1Handler) server.getSocketHandler();
        handler.setAccpetHandler(new SocketAccpetHandler());
        handler.setCloseHandler(new SocketCloseHandler());
        handler.setReadHandler(new SocketReadHandler());
        handler.setWriteHandler(new SocketWriteHandler());

        server.start();
    }
}
