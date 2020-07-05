package com.huawei.roc.timertask.engine;

/**
 * 定时任务Activer：为应用提供一种可以通过一系列的Activer来组建一条业务流水线的能力
 * @author h00163887
 * 
 */
public abstract class TimerTaskActiver implements Cloneable
{
    // 被激活的次数
    private long count = 0;

    // 定时器启动之前的延时
    private long delay = 1000;

    // 定时器启动之后的时间间隔:硬实现，启动定时器后就不能修改
    private long period = 1000;

    public TimerTaskActiver()
    {
        this.delay = 1000;
        this.period = 1000;
    }

    public TimerTaskActiver(long delay, long period)
    {
        this.delay = delay;
        this.period = period;
    }

    public long getCount()
    {
        return count;
    }

    public long getPeriod()
    {
        return period;
    }

    public void setPeriod(long period)
    {
        this.period = period;
    }

    public long getDelay()
    {
        return delay;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    public boolean init()
    {
        return true;
    }

    public boolean destory()
    {
        return true;
    }

    /**
     * 激活Activer:执行动作
     */
    public boolean active()
    {
        count++;
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        TimerTaskActiver clone = (TimerTaskActiver) super.clone();

        clone.delay = this.delay;
        clone.period = this.period;
        clone.count = this.count;

        return clone;
    }
}