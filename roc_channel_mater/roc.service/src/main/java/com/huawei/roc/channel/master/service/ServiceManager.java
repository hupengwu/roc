
package com.huawei.roc.channel.master.service;

import java.util.ArrayList;
import java.util.List;

import com.huawei.roc.channel.master.service.south.service.nodeactivite.NodeActivateRecver;
import com.huawei.roc.channel.master.service.south.service.noderegiste.NodeRegisteRecver;

public class ServiceManager {
    private static List<Object> southServices;

    private static List<Object> northServices;

    public static List<Object> getSouthServices() {
        if (southServices == null) {
            southServices = new ArrayList<Object>();

            southServices.add(new NodeActivateRecver());
            southServices.add(new NodeRegisteRecver());
        }

        return southServices;
    }

    public static List<Object> getNorthServices() {
        if (northServices == null) {
            northServices = new ArrayList<Object>();
        }

        return southServices;
    }

}
