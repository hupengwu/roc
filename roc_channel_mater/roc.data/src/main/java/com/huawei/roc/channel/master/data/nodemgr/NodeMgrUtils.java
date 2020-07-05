
package com.huawei.roc.channel.master.data.nodemgr;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.utils.UUIDUtils;

public class NodeMgrUtils {
    public static void init(KeyValueMapper nodeContainer) {
        for (int i = 0; i < 1; i++) {
            SocketAddress address = new InetSocketAddress("localhost", 10000);
            String uuid = UUIDUtils.getUUID();

            Node node = new Node();
            node.setId(uuid);
            node.setAddress(address);
            node.setConnectSpan(10 * 1000);
            node.setCfgFlagAtMaster(UUIDUtils.getUUID());

            Map<String, Comm> communications = new HashMap<String, Comm>();
            node.setCommunications(communications);
            Comm comm = new Comm();
            comm.setId(UUIDUtils.getUUID());
            communications.put(comm.getId(), comm);

            nodeContainer.put(node.getId(), node);
        }

        return;
    }
}
