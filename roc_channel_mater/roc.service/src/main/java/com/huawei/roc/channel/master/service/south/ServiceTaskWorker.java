
package com.huawei.roc.channel.master.service.south;

import com.huawei.roc.channel.master.service.south.service.nodeactivite.NodeActivateSender;
import com.huawei.roc.channel.master.service.south.service.noderegiste.NodeRegisteSender;
import com.huawei.roc.litetask.worker.LiteAsyncTaskWorker;

public class ServiceTaskWorker extends LiteAsyncTaskWorker {
    /**
     * 线程函数
     */
    public void executThreadFunc() {

        try {
            Thread.sleep(1000);

            // 发送心跳报文
            NodeActivateSender.sendActivate();

            // 发送注册报文
            NodeRegisteSender.sendRegiste();

        } catch (Exception e) {
            e.toString();
        }
    }
}
