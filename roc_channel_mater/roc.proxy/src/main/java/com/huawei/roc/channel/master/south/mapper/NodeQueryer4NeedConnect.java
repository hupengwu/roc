
package com.huawei.roc.channel.master.south.mapper;

import java.net.SocketAddress;
import java.util.Map;

import com.huawei.roc.channel.master.data.nodemgr.Node;
import com.huawei.roc.keyvaluemapper.Readers;

public class NodeQueryer4NeedConnect extends Readers {
    private long currentTime = 0;

    private Map<String, SocketAddress> data;

    public NodeQueryer4NeedConnect(long currentTime, Map<String, SocketAddress> data) {
        this.currentTime = currentTime;
        this.data = data;
    }

    /**
     * 找到目标对象
     * 
     * @param value 元素
     * @return 是否修改成功
     */
    public boolean readValue(Object key, Object value) {
        if (key == null || value == null) {
            return false;
        }

        String uuid = (String) key;
        Node node = (Node) value;

        if (node.getSocketChannel() == null) {
            if (this.currentTime - node.getConnectTime() > node.getConnectSpan()) {
                this.data.put(uuid, node.getAddress());
                return true;
            }
        }

        return false;
    }
}