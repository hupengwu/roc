package com.huawei.roc.timertask.engine;

import java.util.Timer;

/**
 * 定时任务引擎：驱动任务流水线上的各Activer往前运转
 * @author h00163887
 * 
 */
public class TimerTaskEngine
{
 //   private static final Logger logger = OssLogFactory.getLogger(TimerTaskEngine.class);

    private Timer timer = null;

    private TimerTaskActiver atciver = null;

    class TimerTask extends java.util.TimerTask
    {
        @Override
        public void run()
        {
            try
            {
                // 定时激活
                atciver.active();
            }
            catch (Throwable e) // NOPMD
            {
          //      logger.error("[tb]TimerTask active failed.", e);
            }
        }
    }

    public TimerTaskActiver getActiver()
    {
        return this.atciver;
    }

    /**
     * 启动定时器
     * @param delay
     * @param period
     */
    public void init(TimerTaskActiver atciver, long delay, long period)
    {
        this.atciver = atciver;

        try
        {
            this.atciver.init();
        }
        catch (Throwable e) // NOPMD
        {
      //      logger.error("[tb]TimerTaskActiver init failed.", e);
        }

        if (this.timer != null)
        {
            this.timer.cancel();
            this.timer = null;
        }

        this.timer = new Timer();
        this.timer.schedule(new TimerTask(), delay, period);
    }

    /**
     * 销毁定时器
     */
    public void destroy()
    {
        if (this.timer != null)
        {
            this.timer.cancel();
            this.timer = null;
        }

        this.atciver.destory();
        this.atciver = null;
    }
}
