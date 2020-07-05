/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.litetask.worker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.huawei.roc.log.ILogger;
import com.huawei.roc.log.JalorLoggerFactory;

/**
 * 轻量级的异步任务执行器:使用多线程循环处理来解决一批后台任务的问题<br>
 * 背景:很多场景下需要建立一批后台线程，进行后台任务的循环处理，需要降低复杂性<br>
 * 说明:必须实现executThreadFunc函数<br>
* @author h00163887
* @since 2019/09/21
*/
public abstract class LiteAsyncTaskWorker {
    // 日志
    private static final ILogger logger = JalorLoggerFactory.getLogger(LiteAsyncTaskWorker.class);

    /**
     * 需要实现的函数接口方法
     */
    public abstract void executThreadFunc();

    // 线程池
    private ExecutorService threadPool = null;

    // 线程工作者
    private List<TaskWorker> workers = new ArrayList<TaskWorker>();

    /**
     * 创建不断循环的异步任务
     * @param asyncTaskWorker 实际工作的任务函数
     * @param threads  线程数量
     */
    public void createAsyncTask(int threads) {
        this.threadPool = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            TaskWorker worker = new TaskWorker(this);
            threadPool.execute(worker);
            workers.add(worker);
        }
    }

    /**
     * 请求安全的退出各循环线程
     */
    public void reqExit() {
        for (TaskWorker worker : this.workers) {
            worker.reqExit();
        }
    }

    /**
     * 各线程是否已经完全退出
     * @return 是否已经退出
     */
    public boolean isExit() {
        for (TaskWorker worker : this.workers) {
            if (worker.isExit == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * 关闭线程：通知线程完成任务后，安全的退出
     * @throws InterruptedException 异常
     */
    public void close() throws InterruptedException {
        this.reqExit();
        while (!this.isExit()) {
            Thread.sleep(10);
        }
        this.shutdown();
    }

    /**
     * 关闭线程池：强制关闭线程池，最好参考close的方法，如果在退出阶段实在等不下去，只能强制关闭
     */
    public void shutdown() {
        threadPool.shutdown();
    }

    /**
     * 工作者线程
     * @author h00163887
     *
     */
    class TaskWorker implements Runnable {
        // 异步任务工作者
        private LiteAsyncTaskWorker asyncTaskWorker = null;

        // 请求退出任务的标记
        private boolean reqExit = false;

        // 已经退出任务的标识
        private boolean isExit = false;

        /**
         * 请求退出
         */
        public void reqExit() {
            reqExit = true;
        }

        /**
         * 是否已经退出
         * @return 是否已经退出
         */
        public boolean isExit() {
            return isExit;
        }

        /**
         * 构造函数
         * @param asyncTaskWorker 异步工作者
         */
        public TaskWorker(LiteAsyncTaskWorker asyncTaskWorker) {
            this.asyncTaskWorker = asyncTaskWorker;
        }

        /**
         * 线程的run函数
         */
        @Override
        public void run() {
            try {
                executThread(this);
                this.isExit = true;
            } catch (Exception e) {
                logger.error("execut thread worker exception: ", e);
            }
        }

    }

    /**
     * 线程函数
     */
    private static void executThread(TaskWorker taskWorker) {
        while (true) {
            // 检查是否初始化了接口
            if (taskWorker.asyncTaskWorker == null) {
                break;
            }
            
            // 处理退出信号
            if (taskWorker.reqExit) {
                break;
            }

            try {
                taskWorker.asyncTaskWorker.executThreadFunc();

                Thread.sleep(10);
            } catch (Exception e) {
                logger.error("executThread Exception:", e);
            }
        }
    }
}