
package com.huawei.roc.channel.node.north.master.task;

import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.huawei.roc.channel.node.ProxyManager;
import com.huawei.roc.channel.node.data.MapperManager;
import com.huawei.roc.channel.node.data.QueueManager;
import com.huawei.roc.frame.socket.task.SocketTask;

public class MasterTask {
    public static void makeGroupBuff2Json() {
        SocketTask socketTask = new SocketTask();
        socketTask.setJsonQueue(QueueManager.getMasterClientJsonRequests());
        socketTask.setSocketMapper(MapperManager.getMasterSocketContainer());

        // 1.将分组块转换成json字符串
        socketTask.makeGroupBuff2Json();
    }

    public static void recvJson2Vo() {
        ConcurrentLinkedQueue<Map<String, Object>> prevQueue = QueueManager.getMasterClientJsonRequests();
        ConcurrentLinkedQueue<Map<SocketChannel, String>> nextQueue = QueueManager.getMasterClientJsonResponds();

        SocketTask socketTask = new SocketTask();
        socketTask.setProxy(ProxyManager.getProxy());
        socketTask.setJsonQueue(prevQueue);
        socketTask.setRespondQueue(nextQueue);

        socketTask.recvJson2Vo();
    }
}