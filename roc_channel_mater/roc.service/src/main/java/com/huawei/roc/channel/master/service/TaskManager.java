/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.master.service;

import com.huawei.roc.channel.master.north.NorthLinkTaskWorker;
import com.huawei.roc.channel.master.service.south.ServiceTaskWorker;
import com.huawei.roc.channel.master.south.task.SouthLinkTaskWorker;

/**
 * 异步任务的实例管理
 * 
 * @author h00442047
 * @since 2019年12月20日
 */
public class TaskManager {
    private static NorthLinkTaskWorker northLinkeTaskWorker = new NorthLinkTaskWorker();

    private static SouthLinkTaskWorker southLinkeTaskWorker = new SouthLinkTaskWorker();

    private static ServiceTaskWorker southServiceTaskWorker = new ServiceTaskWorker();

    public static void init() {
        try {
            northLinkeTaskWorker.createAsyncTask(2);
            southLinkeTaskWorker.createAsyncTask(1);
            southServiceTaskWorker.createAsyncTask(5);
        } catch (Exception e) {
            e.toString();
        }
    }
}
