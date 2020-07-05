
package com.huawei.roc.channel.node.north.master.task;

import com.huawei.roc.channel.node.north.server.task.ServerTask;
import com.huawei.roc.litetask.worker.LiteAsyncTaskWorker;

public class MasterTaskWorker extends LiteAsyncTaskWorker {
    /**
     * 线程函数
     */
    public void executThreadFunc() {

        try {
            Thread.sleep(10);

            // 1.将分组块转换成json字符串
            MasterTask.makeGroupBuff2Json();

            // 2.将Json转换成结构化数据
            MasterTask.recvJson2Vo();

            // 3.将结果返回给客户端
            ServerTask.respondClient();

        } catch (Exception e) {
            e.toString();
        }
    }
}
