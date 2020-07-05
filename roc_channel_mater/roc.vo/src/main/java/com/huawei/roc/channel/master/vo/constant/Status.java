
package com.huawei.roc.channel.master.vo.constant;

/**
 * 返回客户端请求的任务状态标识
 * 
 * @author h00442047
 * @since 2019年12月19日
 */
public class Status {
    // 尚未发送:接收状态
    public static final String accpeted = "accpeted";

    // 尚未发送:等待状态，尚未转发
    public static final String waited = "waited";

    // 已经发送:已经发送，设备尚未回应
    public static final String sended = "sended";

    // 已经回应：设备已经回应，尚未被取走
    public static final String recved = "recved";

    // 不存在：已经被取走
    public static final String notexit = "not exit";

}
