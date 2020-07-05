
package com.huawei.roc.channel.node.service;

import java.util.ArrayList;
import java.util.List;

import com.huawei.roc.channel.node.service.impl.ActiviteService;

public class ServiceManager {
    private static List<Object> services;

    public static List<Object> getServices() {
        if (services == null) {
            services = new ArrayList<Object>();

            services.add(new ActiviteService());
        }

        return services;
    }

}
