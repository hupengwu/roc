
package com.huawei.roc.channel.master.vo.south;

public class NodeVO {
    /**
     * 节点的ID
     */
    private String nodeId;

    /**
     * 节点类型
     */
    private String nodeType;

    /**
     * 节点状态
     */
    private String nodeStatus;

    /**
     * 节点状态
     */
    private String cfgFlag;

    /**
     * 心跳时间
     */
    private Long heartbeat;

    public String getCfgFlag() {
        return cfgFlag;
    }

    public void setCfgFlag(String cfgFlag) {
        this.cfgFlag = cfgFlag;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Long getHeartbeat() {
        return heartbeat;
    }

    public void setHeartbeat(Long heartbeat) {
        this.heartbeat = heartbeat;
    }

}
