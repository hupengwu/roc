
package com.huawei.roc.channel.master.vo.north;

import java.nio.channels.SocketChannel;

public class BaseVO {
    /**
     * 发送者的身份ID
     */
    private String clientId;

    /**
     * 发送者的channel
     */
    private SocketChannel channel;

    /**
     * 请求报文的唯一Key
     */
    private String requestKey;

    /**
     * 回执的key
     */
    private String receiptKey;

    /**
     * 设备连接参数
     */
    private ConnectVO connect;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 响应参数
     */
    private String respondParam;

    public String getRespondParam() {
        return respondParam;
    }

    public void setRespondParam(String respondParam) {
        this.respondParam = respondParam;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public String getReceiptKey() {
        return receiptKey;
    }

    public void setReceiptKey(String receiptKey) {
        this.receiptKey = receiptKey;
    }

    public ConnectVO getConnect() {
        return connect;
    }

    public void setConnect(ConnectVO connect) {
        this.connect = connect;
    }
}