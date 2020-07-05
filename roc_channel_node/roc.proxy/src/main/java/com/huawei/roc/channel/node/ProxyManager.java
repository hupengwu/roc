
package com.huawei.roc.channel.node;

import java.util.List;

import com.huawei.roc.restlike.RESTFulFactory;
import com.huawei.roc.restlike.RESTFulPorxy;

public class ProxyManager {
    private static RESTFulPorxy proxy;

    public static RESTFulPorxy getProxy() {
        return proxy;
    }

    public static void setProxy(RESTFulPorxy proxy) {
        ProxyManager.proxy = proxy;
    }

    public static void init(List<Object> serviceList) {

        proxy = RESTFulFactory.buildPorxy(serviceList);
    }

}
