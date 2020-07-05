package com.huawei.roc.timertask.serial.error;

import com.huawei.roc.timertask.serial.SerialMessage;

/**
 * 异常消息：能够绑定来源消息对象以及出错原因<br>
 * 注意： <br>
 * 1、异常消息是系统内置消息，不允许派生<br>
 * 2、其父类就是消息废品对象本身<br>
 * 3、可以从error得知废品产生的原因<br>
 * @author h00163887
 * 
 */
public final class SerialErrorMessage extends SerialMessage
{
    // 异常：变成废品的原因
    private Exception error = null;

    public void setError(Exception e)
    {
        this.error = e;
    }

    public Exception getError()
    {
        return this.error;
    }
}
