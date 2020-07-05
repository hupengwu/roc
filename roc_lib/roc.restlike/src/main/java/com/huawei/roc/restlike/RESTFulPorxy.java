
package com.huawei.roc.restlike;

import java.util.Map;

/**
 * 对RESTAPI的请求/响应进行映射表管理
 * 
 * @author h00442047
 * @since 2020年1月19日
 */
public class RESTFulPorxy {
    private Map<RESTFulRequester, RESTFulResponder> mapping;

    public RESTFulPorxy(Map<RESTFulRequester, RESTFulResponder> mapping) {
        this.mapping = mapping;

    }

    public synchronized RESTFulResponder getResponder(RESTFulRequester requester) {
        return mapping.get(requester);
    }

    public synchronized RESTFulResponder getResponder(String resource, String method) {
        RESTFulRequester requester = new RESTFulRequester();
        requester.setMethod(method);
        requester.setResource(resource);
        return mapping.get(requester);
    }
}
