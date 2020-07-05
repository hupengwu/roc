package com.huawei.roc.timertask.serial;

/**
 * 串行消息：走在串行总线上的消息对象，即流水线上的被加工对象。<br>
 * <1> 在设计上不允许实例化克隆接口，否则将引出很多不必要的复杂性。 <br>
 * <2> 生成该消息对象的Activer，在将该消息放入总线之后，不要再访问该消息对象，否则很容易陷入死循环<br>
 * @author h00163887
 * 
 */
public class SerialMessage
{
    // 参数：消息参数
    private Object param = null;

    // 消息发送者：一般是用于追溯是哪个发送者构造了该消息对象
    private SerialActiver sender = null;

    // 消息接受者：一般是用于标记是哪个接收者来处理该消息
    private SerialActiver recver = null;

    public void setSender(SerialActiver activer)
    {
        this.sender = activer;
    }

    public SerialActiver getSender()
    {
        return this.sender;
    }

    public void setRecver(SerialActiver activer)
    {
        this.recver = activer;
    }

    public SerialActiver getRecver()
    {
        return this.recver;
    }

    public void setParam(Object param)
    {
        this.param = param;
    }

    public Object getParam()
    {
        return this.param;
    }

    /**
     * 引用其他对象的属性
     * @param other
     */
    public void refOther(SerialMessage other)
    {
        this.param = other.param;
        this.sender = other.sender;
        this.recver = other.recver;
    }

}
