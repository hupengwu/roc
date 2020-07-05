package com.huawei.roc.timertask.serial;

import com.huawei.roc.timertask.engine.TimerTaskActiver;

/**
 * 串行总线上的Activer：流水线上的加工者。 <br>
 * 1、Activer通过向串行总线上注册关注的任务，进入流水线的位置进行等待任务。<br>
 * 2、当消息对象被放置到流水线上的时候，流水线会往下传输，流水线会调用Activer加工消息对象。<br>
 * 3、Activer在处理完成消息对象之后，会生成新的消息对象，重新放置到流水线。<br>
 * 4、流水线会调度下一环节的Activer去处理新的消息对象。<br>
 * 5、2～4周而复始，原始消息被流水线不断的往下输送，被不断的各Activer加工成各种中间消息，直到成为最终消息。<br>
 * @author h00163887
 * 
 */
public class SerialActiver extends TimerTaskActiver
{
    // 待处理的消息：从消息总线上接收到的消息
    private SerialMessage msg = null;

    // 消息总线：如果需要通知下一个环节处理新的消息，可以发送到该消息总线上
    private SerialMsgBus bus = null;

    /**
     * 通知消息：由客户端或者上一环节的Activer调用
     * @param param 请求携带的参数
     * @return
     */
    public void setMsg(SerialMessage msg)
    {
        this.msg = msg;
    }

    /**
     * 获取消息
     * @return
     */
    public SerialMessage getMsg()
    {
        return this.msg;
    }

    /**
     * 获取消息总线
     * @return
     */
    public SerialMsgBus getBus()
    {
        return this.bus;
    }

    /**
     * 设置消息总线
     * @param bus
     */
    public void setBus(SerialMsgBus bus)
    {
        this.bus = bus;
    }

    /**
     * 销毁Activer
     */
    @Override
    public boolean destory()
    {
        return true;
    }

    /**
     * 克隆
     */
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        SerialActiver clone = (SerialActiver) super.clone();

        return clone;
    }
}