
package com.huawei.roc.channel.master.data.dao;

import java.net.SocketAddress;

public class RocChannelNodeTVO {
    /**
     * 节点的ID
     */
    private String id;

    /**
     * 地址
     */
    private SocketAddress address;

    /**
     * 尝试连接的时间间隔
     */
    private long connectSpan = 10 * 1000L;

    /**
     * 注册时间
     */
    private long registerTime = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SocketAddress getAddress() {
        return address;
    }

    public void setAddress(SocketAddress address) {
        this.address = address;
    }

    public long getConnectSpan() {
        return connectSpan;
    }

    public void setConnectSpan(long connectSpan) {
        this.connectSpan = connectSpan;
    }

    public long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(long registerTime) {
        this.registerTime = registerTime;
    }

}
