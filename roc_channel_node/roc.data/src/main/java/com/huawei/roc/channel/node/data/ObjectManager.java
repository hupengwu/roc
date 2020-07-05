
package com.huawei.roc.channel.node.data;

public class ObjectManager {
    private static ObjectManager instance = new ObjectManager();

    public static ObjectManager instance() {
        return instance;
    }

    /**
     * 节点的ID
     */
    private String nodeId = "";

    /**
     * 节点状态
     */
    private String nodeCfgFlag = "";

    public synchronized String getNodeId() {
        return nodeId;
    }

    public synchronized void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public synchronized String getNodeCfgFlag() {
        return nodeCfgFlag;
    }

    public synchronized void setNodeCfgFlag(String nodeCfgFlag) {
        this.nodeCfgFlag = nodeCfgFlag;
    }
}
