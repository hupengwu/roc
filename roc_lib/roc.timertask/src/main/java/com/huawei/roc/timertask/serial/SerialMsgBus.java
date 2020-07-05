package com.huawei.roc.timertask.serial;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 串行消息总线:承载消息结果的管道
 * @author h00163887
 * 
 */
public final class SerialMsgBus
{
    // 队列的阻塞尺寸：队列最大容量
    private int blockQueus = 1024;

    // 队列大小：队列当前容量
    private int sizeQueus = 0;

    // 消息队列
    private Queue<SerialMessage> messageList = new LinkedList<SerialMessage>();

    // 消息的响应者
    private Map<String, SerialActiver> msgtype2recver = new HashMap<String, SerialActiver>();

    private String name = "";

    public SerialMsgBus(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    /**
     * 注册消息响应者
     * @param msgType
     * @param activer
     */
    public synchronized void setActiver(SerialMessage msg, SerialActiver activer)
    {
        this.msgtype2recver.put(msg.getClass().toString(), activer);
        activer.setBus(this);
    }

    /**
     * 是否存在Activer
     * @param msgType
     * @return
     */
    public synchronized <T> boolean hasActiver(Class<T> clazz)
    {
        return this.msgtype2recver.containsKey(clazz.toString());
    }

    /**
     * 取出消息:由消息接收者调用
     * @return
     */
    public synchronized SerialMessage popMessage()
    {
        SerialMessage msg = messageList.poll();
        if (msg != null)
        {
            this.sizeQueus--;
        }

        return msg;
    }

    /**
     * 消息队列是否拥塞
     * @param blockSize :阻塞的大小标准
     * @return
     */
    public synchronized boolean isBlock()
    {
        return this.sizeQueus > this.blockQueus;
    }

    /**
     * 插入消息：由消息发送者调用,该消息发送者是总线外部的发送者，是消息总线的入口位置送入的消息
     * @param msg
     */
    public synchronized boolean putMessage(SerialMessage msg)
    {
        return this.putMessage(msg, null);
    }

    /**
     * 插入消息：由消息发送者调用,该消息发送者是总线内部的发送者，是消息总线的中间位置生成的消息
     * @param msg
     * @param sender
     * @return
     */
    public synchronized boolean putMessage(SerialMessage msg, SerialActiver sender)
    {
        // 检查：是否进入阻塞状态
        if (this.sizeQueus > this.blockQueus)
        {
            return false;
        }

        // 检查：消息响应者是否存在
        SerialActiver recver = this.msgtype2recver.get(msg.getClass().toString());
        if (recver == null)
        {
            return false;
        }

        msg.setRecver(recver);
        msg.setSender(sender);
        messageList.offer(msg);
        sizeQueus++;

        return true;
    }

    /**
     * 插入消息：由消息发送者调用,该消息发送者是总线外部的发送者，是消息总线的入口位置送入的消息
     * @param msgList 消息列表
     * @return
     */
    public synchronized boolean putMessage(List<SerialMessage> msgList)
    {
        return this.putMessage(msgList, null);
    }

    /**
     * 插入消息：由消息发送者调用,该消息发送者是总线内部的发送者，是消息总线的中间位置生成的消息
     * @param msgList 消息列表
     * @param sender 发送者
     * @return
     */
    public synchronized boolean putMessage(List<SerialMessage> msgList, SerialActiver sender)
    {
        // 检查：是否进入阻塞状态
        if (this.sizeQueus > this.blockQueus)
        {
            return false;
        }

        for (SerialMessage msg : msgList)
        {
            // 检查：消息响应者是否存在
            SerialActiver recver = this.msgtype2recver.get(msg.getClass().toString());
            if (recver == null)
            {
                return false;
            }

            msg.setSender(sender);
            msg.setRecver(recver);
        }

        messageList.addAll(msgList);
        sizeQueus += msgList.size();

        return true;
    }
}
