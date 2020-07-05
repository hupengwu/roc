
package com.huawei.roc.channel.master.south.task;

import com.huawei.roc.litetask.worker.LiteAsyncTaskWorker;

public class SouthLinkTaskWorker extends LiteAsyncTaskWorker {
    /**
     * 线程函数
     */
    public void executThreadFunc() {

        try {
            Thread.sleep(10);

            // 1.尝试连接未连接的节点
            SouthTask.tryConnect();

            // 2.将分组块转换成json字符串
            SouthTask.makeGroupBuff2Json();

            // 3.处理响应报文
            SouthTask.recvRespond();

        } catch (Exception e) {
            e.toString();
        }
    }
}
