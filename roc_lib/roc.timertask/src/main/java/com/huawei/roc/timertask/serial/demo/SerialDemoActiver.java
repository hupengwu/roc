package com.huawei.roc.timertask.serial.demo;

import com.huawei.roc.timertask.asynch.AsynchEngine;
import com.huawei.roc.timertask.serial.SerialActiver;
import com.huawei.roc.timertask.serial.SerialEngine;
import com.huawei.roc.timertask.serial.SerialMsgBus;
import com.huawei.roc.timertask.serial.error.SerialErrorMessage;

/**
 * 自定义消息处理者：必须重载getName方法和active方法
 * @author h00163887
 * 
 */
public class SerialDemoActiver extends SerialActiver
{
    private Integer value = 0;

    /**
     * 激活Activer:执行动作
     */
    @Override
    public boolean active()
    {
        super.active();

        try
        {
            Thread.sleep(1000);
        }
        catch (Exception e)
        {
            // logger.info(e.toString());
        }

        // 简单处理
        SerialDemoMessage.decode(this.getMsg());

        this.value++;
        if (this.getBus() != null && this.value < 5)
        {
            // 将消息体重新发到消息总线上，通知下一环节的Active处理消息
            this.getBus().putMessage(this.getMsg(), this);

            System.out.print(this.value.toString() + " " + this.getBus().getName() + "\n");
        }
        else
        {
            System.out.print("end...." + this.getBus().getName() + "\n");
        }

        return true;
    }

    public static void main(String[] args)
    {
        // 定义一个全局的通用异常总线
        SerialMsgBus errBus = new SerialMsgBus("errbus:msg");

        // 定义一个异步引擎：装载串行引擎
        AsynchEngine asynchEngine = new AsynchEngine();

        // <1>定义全局的异常总线
        // 异常串行总线引擎
        SerialEngine errorEngine = new SerialEngine(errBus, new SerialMsgBus("errbus:err"));
        asynchEngine.registActiver(errorEngine, "ErrorEngine");

        // 注册异常消息和消息处理者
        SerialDemoErrorActiver serialErrorActiver = new SerialDemoErrorActiver();
        SerialErrorMessage serialErrorMessage = new SerialErrorMessage();
        errBus.setActiver(serialErrorMessage, serialErrorActiver);

        // <2>定义第1条业务总线：指定全局异常总线为引擎的异常总线
        // 普通串行总线引擎
        SerialEngine serialEngine1 = new SerialEngine(new SerialMsgBus("bus1"), errBus);
        asynchEngine.registActiver(serialEngine1, "NormalEngine1");

        // 获得串行引擎上的消息总线
        SerialMsgBus msgBus1 = serialEngine1.getMsgBus();

        // 注册自定义消息和消息处理者
        SerialDemoActiver serialDemoAcviter1 = new SerialDemoActiver();
        SerialDemoMessage serialDemoMessage1 = new SerialDemoMessage();
        msgBus1.setActiver(serialDemoMessage1, serialDemoAcviter1);

        // 将正确的自定义消息送入消息总线，会被SerialDemoAcviter处理
        serialDemoMessage1 = new SerialDemoMessage();
        serialDemoMessage1.setParam(1);
        msgBus1.putMessage(serialDemoMessage1);

        // 将非法的自定义消息送入消息总线，会出现异常，被送入异常总线，被SerialErrorAcviter处理
        serialDemoMessage1 = new SerialDemoMessage();
        serialDemoMessage1.setParam(1.1);
        msgBus1.putMessage(serialDemoMessage1);

        // <3>定义第2条业务总线：指定全局异常总线为引擎的异常总线
        // 普通串行总线引擎
        SerialEngine serialEngine2 = new SerialEngine(new SerialMsgBus("bus2"), errBus);
        asynchEngine.registActiver(serialEngine2, "NormalEngine2");

        // 获得串行引擎上的消息总线
        SerialMsgBus msgBus2 = serialEngine2.getMsgBus();

        // 注册自定义消息和消息处理者
        SerialDemoActiver serialDemoAcviter2 = new SerialDemoActiver();
        SerialDemoMessage serialDemoMessage2 = new SerialDemoMessage();
        msgBus2.setActiver(serialDemoMessage2, serialDemoAcviter2);

        // 将正确的自定义消息送入消息总线，会被SerialDemoAcviter处理
        serialDemoMessage2 = new SerialDemoMessage();
        serialDemoMessage2.setParam(1);
        msgBus2.putMessage(serialDemoMessage2);

        // 将非法的自定义消息送入消息总线，会出现异常，被送入异常总线，被SerialErrorAcviter处理
        serialDemoMessage2 = new SerialDemoMessage();
        serialDemoMessage2.setParam(1.1);
        msgBus2.putMessage(serialDemoMessage2);

        try
        {
            Thread.sleep(1000 * 1000 * 10);
        }
        catch (Exception e)
        {
            // logger.info(e.toString());
        }
    }

}
