package com.huawei.roc.timertask.serial.demo;

import com.huawei.roc.timertask.serial.SerialMessage;

/**
 * 自定义消息：必须重载getName方法
 * @author h00163887
 * 
 */
public class SerialDemoMessage extends SerialMessage
{
    public static Integer decode(SerialMessage msg)
    {
        return (Integer) msg.getParam();
    }
}