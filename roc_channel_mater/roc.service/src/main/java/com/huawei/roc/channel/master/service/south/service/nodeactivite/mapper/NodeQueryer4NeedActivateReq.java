
package com.huawei.roc.channel.master.service.south.service.nodeactivite.mapper;

import java.nio.channels.SocketChannel;
import java.util.Map;

import com.huawei.roc.channel.master.data.nodemgr.Node;
import com.huawei.roc.keyvaluemapper.Readers;

public class NodeQueryer4NeedActivateReq extends Readers {
    private long currentTime = 0;

    private long span = 0;

    private Map<String, SocketChannel> data;

    public NodeQueryer4NeedActivateReq(long currentTime, long span, Map<String, SocketChannel> data) {
        this.currentTime = currentTime;
        this.data = data;
        this.span = span;
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

        if (node.getSocketChannel() != null) {
            if (this.currentTime - node.getActiveReqTime() > this.span) {
                this.data.put(uuid, node.getSocketChannel());
                return true;
            }
        }

        return false;
    }
}
