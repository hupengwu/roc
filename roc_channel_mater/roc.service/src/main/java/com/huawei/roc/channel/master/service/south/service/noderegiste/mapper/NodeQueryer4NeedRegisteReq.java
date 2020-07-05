
package com.huawei.roc.channel.master.service.south.service.noderegiste.mapper;

import java.nio.channels.SocketChannel;
import java.util.Map;

import com.huawei.roc.channel.master.data.nodemgr.Node;
import com.huawei.roc.keyvaluemapper.Readers;

public class NodeQueryer4NeedRegisteReq extends Readers {
    private long currentTime = 0;

    private long span = 0;

    private Map<String, SocketChannel> data;

    public NodeQueryer4NeedRegisteReq(long currentTime, long span, Map<String, SocketChannel> data) {
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

        if (node.getSocketChannel() == null) {
            return false;
        }

        if (this.currentTime - node.getRegisteReqTime() < this.span) {
            return false;
        }
        if (node.getCfgFlagAtMaster() == null) {
            return false;
        }
        if (!node.getCfgFlagAtMaster().equals(node.getCfgFlagAtNode())) {
            return false;
        }

        this.data.put(uuid, node.getSocketChannel());

        return true;
    }
}
