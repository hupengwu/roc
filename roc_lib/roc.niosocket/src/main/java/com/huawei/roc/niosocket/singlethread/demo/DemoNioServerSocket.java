
package com.huawei.roc.niosocket.singlethread.demo;

import com.huawei.roc.niosocket.singlethread.socket.NioServerSocket;

public class DemoNioServerSocket {
    public static void main(String[] args) {

        NioServerSocket nioServerSocket = new NioServerSocket();
        nioServerSocket.selector();
    }
}
