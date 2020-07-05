/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.node.north.server.task;

import com.huawei.roc.litetask.worker.LiteAsyncTaskWorker;

/**
 * 异步任务处理器
 * 
 * @author h00442047
 * @since 2019年12月19日
 */
public class ServerTaskWorker extends LiteAsyncTaskWorker {
    /**
     * 线程函数
     */
    public void executThreadFunc() {

        try {
            Thread.sleep(10);

            // 1.将分组块转换成json字符串
            ServerTask.makeGroupBuff2Json();

            // 2.将Json转换成结构化数据
            ServerTask.recvJson2Vo();

            // 3.将结果返回给客户端
            ServerTask.respondClient();

        } catch (Exception e) {
            e.toString();
        }
    }
}
