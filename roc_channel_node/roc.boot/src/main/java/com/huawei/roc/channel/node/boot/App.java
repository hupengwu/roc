
package com.huawei.roc.channel.node.boot;

import com.huawei.roc.channel.node.ProxyManager;
import com.huawei.roc.channel.node.SocketManager;
import com.huawei.roc.channel.node.TaskManager;
import com.huawei.roc.channel.node.service.ServiceManager;

/**
 * 启动进程：java -jar roc.channel.node.boot-1.0.0-SNAPSHOT.jar
 *工程依赖关系：roc.channel.vo/roc.channel.node.data/roc.channel.node.impl/roc.channel.node.boot
 *
 * @author h00442047
 * @since 2019年12月20日
 */
public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("begin roc.channel.node.boot start!");

        SocketManager.init();
        TaskManager.init();
        ProxyManager.init(ServiceManager.getServices());

        System.out.println("start roc.channel.node.boot finish!");
    }
}
