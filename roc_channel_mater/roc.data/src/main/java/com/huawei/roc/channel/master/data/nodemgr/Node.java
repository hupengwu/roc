
package com.huawei.roc.channel.master.data.nodemgr;

import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * Node的相关信息
 * 
 * @author h00442047
 * @since 2019年12月26日
 */
public class Node {
    /**
     * 节点的ID
     */
    private String id;

    /**
     * 最近的配置状态（Master侧）：UUID，用来标识是最近哪次修改，这样node/master和上层应用就知道配置是否发生了变化，然后要不要重新同步
     */
    private String cfgFlagAtMaster;

    /**
     * 最近的配置状态（Node侧）：UUID，用来标识是最近哪次修改，这样node/master和上层应用就知道配置是否发生了变化，然后要不要重新同步
     */
    private String cfgFlagAtNode;

    /**
     * 地址
     */
    private SocketAddress address;

    /**
     * 连接到时间
     */
    private long connectTime = 0;

    /**
     * 尝试连接的时间间隔
     */
    private long connectSpan = 10 * 1000L;

    /**
     * 注册时间
     */
    private long registerTime = 0;

    /**
     * 上次心跳请求时间
     */
    private long activeReqTime = 0;

    /**
     * 上次心跳接收时间
     */
    private long activeRspTime = 0;

    /**
     * 上次注册请求时间
     */
    private long registeReqTime = 0;

    /**
     * 上次注册接收时间
     */
    private long registeRspTime = 0;

    /**
     * socket
     */
    private SocketChannel socketChannel;

    /**
     * 通信参数
     */
    private Map<String, Comm> communications;

    public long getRegisteReqTime() {
        return registeReqTime;
    }

    public void setRegisteReqTime(long registeReqTime) {
        this.registeReqTime = registeReqTime;
    }

    public long getRegisteRspTime() {
        return registeRspTime;
    }

    public void setRegisteRspTime(long registeRspTime) {
        this.registeRspTime = registeRspTime;
    }

    public String getCfgFlagAtNode() {
        return cfgFlagAtNode;
    }

    public void setCfgFlagAtNode(String cfgFlagAtNode) {
        this.cfgFlagAtNode = cfgFlagAtNode;
    }

    public String getCfgFlagAtMaster() {
        return cfgFlagAtMaster;
    }

    public void setCfgFlagAtMaster(String cfgFlagAtMaster) {
        this.cfgFlagAtMaster = cfgFlagAtMaster;
    }

    public Map<String, Comm> getCommunications() {
        return communications;
    }

    public void setCommunications(Map<String, Comm> communications) {
        this.communications = communications;
    }

    public long getActiveRspTime() {
        return activeRspTime;
    }

    public void setActiveRspTime(long activeRspTime) {
        this.activeRspTime = activeRspTime;
    }

    public long getActiveReqTime() {
        return activeReqTime;
    }

    public void setActiveReqTime(long activeReqTime) {
        this.activeReqTime = activeReqTime;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

    public long getConnectSpan() {
        return connectSpan;
    }

    public void setConnectSpan(long connectSpan) {
        this.connectSpan = connectSpan;
    }
}
