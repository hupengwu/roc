
package com.huawei.roc.litetask.demo;

import com.huawei.roc.litetask.worker.LiteAsyncTaskWorker;

public class Demo2AsyncTaskWorker extends LiteAsyncTaskWorker {

    /**
     * 重载线程函数，涉及被多线程访问的属性，都要加锁<br>
     */
    public void executThreadFunc() {
        System.out.print("abc\r\n");
    }

    public static void main(String[] args) {
        Demo2AsyncTaskWorker asyncTask = new Demo2AsyncTaskWorker();
        asyncTask.createAsyncTask(10);
        try {
            Thread.sleep(1000);

            asyncTask.close();

        } catch (Exception e) {

        }
    }

}