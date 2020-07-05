
package com.huawei.roc.niosocket.multithread.demo;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.huawei.roc.niosocket.multithread.handler.level1.SocketL1Handler;
import com.huawei.roc.niosocket.multithread.handler.level2.SocketReadHandler;
import com.huawei.roc.niosocket.multithread.socket.NioClientSocket;

public class DemoClient {
    public static void main(String[] args) {
        try {
            NioClientSocket clientSocket = new NioClientSocket();
            SocketL1Handler handler = (SocketL1Handler) clientSocket.getSocketHandler();
            handler.setReadHandler(new SocketReadHandler());
            clientSocket.start();

            SocketChannel socketChannel = null;
            while (true) {
                Thread.sleep(1000);

                if (!clientSocket.isConnected(socketChannel)) {
                    try {
                        // 连接目标服务器
                        socketChannel = clientSocket.connect(new InetSocketAddress("localhost", 10001));
                    } catch (Exception e) {
                        e.toString();
                        continue;
                    }
                }

                byte[] buff = "abc".getBytes();//HexUtils.hexStringToByteArray("fa7b22636c69656e744964223a22636c696e65742d3031227dfb");
                clientSocket.writeChannel(socketChannel, buff);
            }

        } catch (Exception e) {
            e.toString();
        }
    }
}
