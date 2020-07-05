
package com.huawei.roc.litetask.demo;

import java.util.ArrayList;
import java.util.List;

import com.huawei.roc.litetask.packet.LiteSyncTaskMapPacket;
import com.huawei.roc.litetask.worker.LiteSyncTaskWorker;

public class Demo1SyncTaskMapPacket extends LiteSyncTaskMapPacket {

    /**
     * 执行任务:注意，涉及被多线程访问的属性，都要加锁<br>
     * @param content
     * @return
     */
    @Override
    public void executFunction(Object content) {
        try {

            Thread.sleep(1000);

            System.out.println(content);

            // 带回结果
            this.addResult(content, content.toString());
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void main(String[] args) throws Exception {

        // 测试：100个任务对象的列表，对象类型为Integer
        List<Integer> dateList = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            dateList.add(i);
        }

        // 测试任务：
        Demo1SyncTaskListPacket packet = new Demo1SyncTaskListPacket();
        packet.setTaskList(dateList);

        // 定义多线程执行器：10个线程
        LiteSyncTaskWorker executor = new LiteSyncTaskWorker(10);
        executor.executeTask(packet);

        // 取出计算结果
        List<Integer> result = packet.getResultList(Integer.class);
        result.size();
    }

}