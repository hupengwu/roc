
package com.huawei.roc.channel.master;

import java.util.List;

import com.huawei.roc.restlike.RESTFulFactory;
import com.huawei.roc.restlike.RESTFulPorxy;

public class ProxyManager {
    private static RESTFulPorxy southProxy;

    public static RESTFulPorxy getProxy() {
        return southProxy;
    }

    public static void setSouthProxy(RESTFulPorxy proxy) {
        ProxyManager.southProxy = proxy;
    }

    public static void init(List<Object> southList) {

        southProxy = RESTFulFactory.buildPorxy(southList);
    }

}
