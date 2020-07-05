package com.huawei.roc.timertask.serial.demo;

import com.huawei.roc.timertask.serial.SerialActiver;
import com.huawei.roc.timertask.serial.error.SerialErrorMessage;

public class SerialDemoErrorActiver extends SerialActiver
{
    /**
     * 激活Activer:执行动作
     */
    @Override
    public boolean active()
    {
        super.active();

        SerialErrorMessage msg = (SerialErrorMessage) this.getMsg();
        System.out.print(msg.getError().getMessage() + " " + this.getBus().getName() + "\n");
        return true;
    }
}
