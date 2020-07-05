
package com.huawei.roc.channel.master.boot;

import com.huawei.roc.channel.master.ProxyManager;
import com.huawei.roc.channel.master.SocketManager;
import com.huawei.roc.channel.master.data.MapperManager;
import com.huawei.roc.channel.master.service.ServiceManager;
import com.huawei.roc.channel.master.service.TaskManager;

/**
 * 启动进程：java -jar roc.channel.master.boot-1.0.0-SNAPSHOT.jar
 *工程依赖关系：roc.channel.vo/roc.channel.master.data/roc.channel.master.impl/roc.channel.master.boot
 *
 * @author h00442047
 * @since 2019年12月20日
 */
public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("begin roc.channel.master.boot start!");

        String path = App.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        MapperManager.init();
        SocketManager.init();
        TaskManager.init();
        ProxyManager.init(ServiceManager.getSouthServices());

        System.out.println("start roc.channel.master.boot finish!");
    }
}
