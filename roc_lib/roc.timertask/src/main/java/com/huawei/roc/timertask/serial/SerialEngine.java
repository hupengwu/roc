package com.huawei.roc.timertask.serial;

import com.huawei.roc.timertask.engine.TimerTaskActiver;
import com.huawei.roc.timertask.serial.error.SerialErrorMessage;

/**
 * 串行总线引擎：推动消息总线往前传输的马达，它是通过一个定时器线程实现。<br>
 * 1、消息总线：流水线上的传送带，引擎推动的就是该消息总线。用于放置和输送消息，以及安装和调度Activer进行加工。<br>
 * 2、异常总线：用于放置生产线生产的废品。它是另一个生产线的消息总线，不是本生产线的消息总线。<br>
 * 生产线生产出废品的时候，会自动生成一个错误消息将废品消息包装起来，丢弃到异常总线上。<br>
 * @author h00163887
 * 
 */
public class SerialEngine extends TimerTaskActiver
{
    // 消息总线：
    private SerialMsgBus msgBus = null;

    // 异常总线
    private SerialMsgBus errBus = null;

    // 异常处理
    // private SerialErrorActiver errActiver = new SerialErrorActiver();

    public SerialEngine(SerialMsgBus msgBus, SerialMsgBus errBus)
    {
        super();

        this.msgBus = msgBus;
        this.errBus = errBus;

        // 采用100ms级别的定时器，周期性处理任务
        this.setPeriod(100);
    }

    /**
     * 获取消息总线
     * @return
     */
    public SerialMsgBus getMsgBus()
    {
        return this.msgBus;
    }

    /**
     * 获取异常总线
     * @return
     */
    public SerialMsgBus getErrBus()
    {
        return this.errBus;
    }

    /**
     * 捕获异常
     * @param msg
     * @param e
     */
    private void catchError(SerialMessage msg, Exception e)
    {
        // 检查：是不是有异常总线来输送异常
        if (this.errBus == null)
        {
            return;
        }
        // 检查：是不是总线饱和
        if (this.errBus.isBlock())
        {
            return;
        }
        // 检查：是不是有处理者
        if (!this.errBus.hasActiver(SerialErrorMessage.class))
        {
            return;
        }

        SerialErrorMessage error = new SerialErrorMessage();
        error.setError(e);
        error.refOther(msg);

        this.errBus.putMessage(error, null);
    }

    /**
     * 定时激活
     */
    public boolean active()
    {
        SerialMessage msg = this.msgBus.popMessage();
        while (msg != null)
        {
            // 取出接收者：由总线为消息分配
            SerialActiver activer = msg.getRecver();

            try
            {
                // 告知接收者消息参数
                activer.setMsg(msg);

                // 激活接收者处理消息
                if (!activer.active())
                {
                    catchError(msg, null);
                }
            }
            catch (Exception e)
            {
                // 捕获notify/active的异常
                catchError(msg, e);
            }

            // 下一个消息
            msg = this.msgBus.popMessage();
        }

        return true;
    }
}
