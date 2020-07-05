
package com.huawei.roc.channel.master.data;

import com.huawei.roc.channel.master.data.nodemgr.NodeMgrUtils;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;

public class MapperManager {
    /**
     *  1.Socket连接管理：下面挂有数据块缓存
     */
    private static KeyValueMapper southSocketContainer = new KeyValueMapper();

    private static KeyValueMapper northSocketContainer = new KeyValueMapper();

    /**
     * 2.节点管理
     */
    private static KeyValueMapper nodeContainer = new KeyValueMapper();

    public static KeyValueMapper getNodeContainer() {
        return nodeContainer;
    }

    public static KeyValueMapper getSouthSocketContainer() {
        return southSocketContainer;
    }

    public static KeyValueMapper getNorthSocketContainer() {
        return northSocketContainer;
    }

    public static void init() {
        NodeMgrUtils.init(nodeContainer);
        return;
    }
}
