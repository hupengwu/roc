/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.node;

import com.huawei.roc.channel.node.north.master.task.MasterTaskWorker;
import com.huawei.roc.channel.node.north.server.task.ServerTaskWorker;

/**
 * 异步任务的实例管理
 * 
 * @author h00442047
 * @since 2019年12月20日
 */
public class TaskManager {
    /**
     * 异步任务实例
     */
    private static ServerTaskWorker serverTaskWorker = new ServerTaskWorker();

    private static MasterTaskWorker masterTaskWorker = new MasterTaskWorker();

    public static void init() {
        try {
            masterTaskWorker.createAsyncTask(1);
            serverTaskWorker.createAsyncTask(5);
        } catch (Exception e) {
            e.toString();
        }
    }
}
