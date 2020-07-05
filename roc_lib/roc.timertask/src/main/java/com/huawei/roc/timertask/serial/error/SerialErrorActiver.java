package com.huawei.roc.timertask.serial.error;

import com.huawei.roc.timertask.serial.SerialActiver;

/**
 * 通用异常消息处理者：能够处理异常总线上的SerialErrorMessage消息<br>
 * @author h00163887
 * 
 */
public class SerialErrorActiver extends SerialActiver implements Cloneable
{

    /**
     * 激活Activer:执行动作
     */
    @Override
    public boolean active()
    {
        super.active();

        ((SerialErrorMessage) this.getMsg()).getError().getMessage();
        return true;
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
        SerialErrorActiver clone = (SerialErrorActiver) super.clone();

        return clone;
    }

}
