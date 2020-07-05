
package com.huawei.roc.restlike.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huawei.roc.restlike.RESTFulFactory;
import com.huawei.roc.restlike.RESTFulPorxy;
import com.huawei.roc.restlike.RESTFulResponder;

public class DemoRESTFulFactory {
    public static void main(String[] args) {

        IDemoImpl object = new IDemoImpl();

        List<Object> objectList = new ArrayList<Object>();
        objectList.add(object);

        RESTFulPorxy proxy = RESTFulFactory.buildPorxy(objectList);
        RESTFulResponder responder1 = proxy.getResponder("/activity/his/create", "POST");
        RESTFulResponder responder2 = proxy.getResponder("/activity/his/update", "PUT");
        RESTFulResponder responder3 = proxy.getResponder("/activity/his/update", "GET");

        try {
            Map<String, Object> mp = new HashMap<String, Object>();
            mp.put("item", 246);
            responder1.invokeMethod(null, null, "{\"item\":246}", mp);
            responder2.invokeMethod(null, null, "{\"item\":246}", mp);
            responder3.invokeMethod(null, null, "{\"item\":246}", mp);
        } catch (Exception e) {
            e.toString();
        }

    }
}
