
package com.huawei.roc.channel.node.vo.server;

/**
 * 连接参数
 * @author h00442047
 * @since 2019年11月21日
 */
public class ConnectVO {
    /**
     * 接收时间：收到上面客户端报文的时间点
     */
    private Long requestTime;

    /**
     * 接收时间：回应上面客户端的时间点
     */
    private Long respondTime;

    /**
     * 接收时间：收到下面设备端报文的时间点
     */
    private Long recvTime;

    /**
     * 发送时间：发送下面设备端报文的时间点
     */
    private Long sendTime;

    /**
     * 超时：发送下面设备报文的时间间隔
     */
    private Long timeoutInterval;

    /**
     * 超时：发送下面设备报文失败时的重试次数
     */
    private Integer retryMax;

    /**
     * 超时：发送下面设备报文失败时，再次重试时间间隔，单位毫秒
     */
    private Long retryInterval;

    /**
     * 滞留：服务端已经处理完成时间，但是客户端却未取走的开始时间
     */
    private Long heldUpBegin;

    /**
     * 滞留：服务端已经处理完成时间，允许保存的最大时间
     */
    private Long heldUpInterval;

    public Long getHeldUpBegin() {
        return heldUpBegin;
    }

    public void setHeldUpBegin(Long heldUpBegin) {
        this.heldUpBegin = heldUpBegin;
    }

    public Long getHeldUpInterval() {
        return heldUpInterval;
    }

    public void setHeldUpInterval(Long heldUpInterval) {
        this.heldUpInterval = heldUpInterval;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Long requestTime) {
        this.requestTime = requestTime;
    }

    public Long getRespondTime() {
        return respondTime;
    }

    public void setRespondTime(Long respondTime) {
        this.respondTime = respondTime;
    }

    public Long getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(Long recvTime) {
        this.recvTime = recvTime;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getTimeoutInterval() {
        return timeoutInterval;
    }

    public void setTimeoutInterval(Long timeoutInterval) {
        this.timeoutInterval = timeoutInterval;
    }

    public Integer getRetryMax() {
        return retryMax;
    }

    public void setRetryMax(Integer retryMax) {
        this.retryMax = retryMax;
    }

    public Long getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Long retryInterval) {
        this.retryInterval = retryInterval;
    }
}
