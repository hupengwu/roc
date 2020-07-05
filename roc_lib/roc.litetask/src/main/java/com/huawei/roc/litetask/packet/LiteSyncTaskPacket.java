/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.litetask.packet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 轻量级的多线程任务包:使用多线程并行处理来解决一批任务的高强度计算的问题<br>
 * 多线程任务包：任务包裹用于放置多线程处理的输入/输出数据<br>
 * @author h00163887
 * @since 2019/09/21
 */
public abstract class LiteSyncTaskPacket {
    /**
     * 需要处理的任务数据
     */
    private List<Object> tasks = new ArrayList<Object>();

    private int taskSize = 0;

    /**
     * 完成的任务数
     */
    private int completed = 0;

    /**
     * 设置需要处理的一组任务集合
     * @param contents 任务列表
     */
    public synchronized <TASK> void setTaskList(Collection<TASK> contents) {
        for (TASK task : contents) {
            this.tasks.add(task);
        }

        // 保存任务的初始数量
        this.taskSize = this.tasks.size();
    }

    /**
     * 任务是否结束
     * @return 任务是否完成
     */
    public synchronized boolean finished() {
        return this.completed >= this.taskSize;
    }

    /**
     * 取出一个任务
     * @return 任务对象
     */
    public synchronized Optional<Object> popTask() {
        if (tasks.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(tasks.remove(0));
    }

    /**
     * 执行完成的次数
     * @return 完成的数量
     */
    public synchronized int getCompleted() {
        return completed;
    }

    /**
     * 执行完成一次就计数一次
     */
    public synchronized void increment() {
        this.completed++;
    }

    /**
     * 执行任务:注意，涉及被多线程访问的属性，都要加锁<br>
     * @param content 单个任务对象
     */
    public abstract void executFunction(Object content);
}