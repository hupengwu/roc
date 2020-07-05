/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.litetask.worker;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.huawei.roc.litetask.packet.LiteSyncTaskPacket;
import com.huawei.roc.log.ILogger;
import com.huawei.roc.log.JalorLoggerFactory;

/**
 * 轻量级的多线程任务执行器:使用多线程并行处理来解决一批任务的高强度计算的问题<br>
 * 背景：在做通信解析的时候，控制器返回了一大批分组数据，如果以传统的串行方式逐个逐个分组数据进行处理，非常缓慢耗时几分钟<br>
 * ....那么，能不能把这一大批分组数据通过多线程进行并行处理，然后再把结果合并起来，这样处理速度不就快了吗？<br>
 * ....但是摆在面前的问题是，多线程的并发对普通开发人员来说，要控制好它们可是不小的难题，能不能提供一个简单易用的多线程组件呢，LiteTask就运运而生<br>
 * @author h00163887
 * @since 2019/09/21
 */
public class LiteSyncTaskWorker {
    private static final ILogger logger = JalorLoggerFactory.getLogger(LiteSyncTaskWorker.class);

    /**
     * 线程任务数量
     */
    private int nThreads = 10;

    public LiteSyncTaskWorker(int nThreads) {
        this.nThreads = nThreads;
    }

    /**
     * 工作者线程
     * @author h00163887
     *
     */
    static class TaskWorker implements Runnable {
        private LiteSyncTaskPacket taskPacket = null;

        public TaskWorker(LiteSyncTaskPacket taskPacket) {
            this.taskPacket = taskPacket;
        }

        @Override
        public void run() {
            try {
                executThread(this.taskPacket);
            } catch (Exception e) {
                logger.error("execut thread worker met exception: ", e);
            }
        }

    }

    /**
     * 线程函数
     * @param taskPackage 任务包
     * @return 是否成功
     */
    private static boolean executThread(LiteSyncTaskPacket taskPackage) {
        while (true) {
            // 获取一个需要处理的任务对象
            Optional<Object> opt = taskPackage.popTask();

            if (!opt.isPresent()) {
                // 如果没有则退出线程循环
                logger.error("task thread completed!");
                break;
            }

            try {
                // 执行任务
                taskPackage.executFunction(opt.get());
            } catch (Exception e) {
                logger.error("Exception executThread", e);
            } finally {
                // 函数结束之前，计数器加一
                logger.info("executThread end, completed count = " + taskPackage.getCompleted());
                taskPackage.increment();
            }
        }

        return true;
    }

    /**
     * 执行业务
     * @return 是否成功
     */
    public boolean executeTask(LiteSyncTaskPacket packet) {
        // 创建并启动线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);
        for (int i = 0; i < nThreads; i++) {
            TaskWorker worker = new TaskWorker(packet);
            threadPool.execute(worker);
        }

        // 等待线程处理完毕
        while (!packet.finished()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.info("executTask Main Thread Sleep Error");
            }
        }

        threadPool.shutdown();

        logger.info("executTask sucessed!");
        return true;
    }
}