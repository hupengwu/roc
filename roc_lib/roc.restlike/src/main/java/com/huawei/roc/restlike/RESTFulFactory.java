
package com.huawei.roc.restlike;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

public class RESTFulFactory {
    /**
     * 生成Proxy
     * @param objectList API实例化对象列表
     * @return Proxy
     */
    public static RESTFulPorxy buildPorxy(List<Object> objectList) {
        Map<RESTFulRequester, RESTFulResponder> mappings = new HashMap<RESTFulRequester, RESTFulResponder>();

        for (Object object : objectList) {
            Map<RESTFulRequester, RESTFulResponder> mapping = buildApiMapping(object);
            mappings.putAll(mapping);
        }
        return new RESTFulPorxy(mappings);
    }

    /**
     * 根据REST API的实例化对象，生成REST请求和响应的映射表
     * 
     * @param objectList 实例
     * @return 请求/响应映射表
     */
    private static Map<RESTFulRequester, RESTFulResponder> buildApiMapping(Object javaObject) {
        Map<RESTFulRequester, RESTFulResponder> mapping = new HashMap<RESTFulRequester, RESTFulResponder>();

        // 获取该对象类型的API声明信息
        Map<String, Map<String, Method>> url2httpmethod2javamethod = buildApiMappingByImplementsAnno(javaObject.getClass());

        for (Entry<String, Map<String, Method>> entryUrl : url2httpmethod2javamethod.entrySet()) {
            String httpResource = entryUrl.getKey();
            Map<String, Method> httpmethod2javamethod = entryUrl.getValue();

            for (Entry<String, Method> entryHttp : httpmethod2javamethod.entrySet()) {
                String httpMethod = entryHttp.getKey();
                Method javaMethod = entryHttp.getValue();

                // 请求者
                RESTFulRequester requester = new RESTFulRequester();
                requester.setResource(httpResource);
                requester.setMethod(httpMethod);

                // 响应者
                RESTFulResponder responder = new RESTFulResponder();
                responder.setMethod(javaMethod);
                responder.setObject(javaObject);

                mapping.put(requester, responder);
            }
        }

        return mapping;
    }

    /**
     *根据 实现类所相关的接口上的注解，生成API映射表
     * 功能：通过查找对象类型所实现的接口，并根据接口的RESTful风格，生成一个RESTful接口风格映射表
     * 
     * @param calzz 对象类型
     * @return API映射表
     */
    public static Map<String, Map<String, Method>> buildApiMappingByImplementsAnno(Class<?> calzz) {
        Map<String, Map<String, Method>> url2httpmethod2javamethodAll = new HashMap<String, Map<String, Method>>();

        // 类所实现的接口
        Class<?>[] interfaces = calzz.getInterfaces();
        for (Class<?> apiIf : interfaces) {

            // 提取该接口的API映射表
            Map<String, Map<String, Method>> url2httpmethod2javamethod = buildApiMappingByInterfaceAnno(apiIf);

            // 合并到总的API映射表
            for (Entry<String, Map<String, Method>> entryUrl : url2httpmethod2javamethod.entrySet()) {
                String httpResource = entryUrl.getKey();
                Map<String, Method> httpmethod2javamethod = entryUrl.getValue();

                Map<String, Method> httpmethod2javamethodAll = url2httpmethod2javamethodAll.get(httpResource);
                if (httpmethod2javamethodAll == null) {
                    httpmethod2javamethodAll = new HashMap<String, Method>();
                    url2httpmethod2javamethodAll.put(httpResource, httpmethod2javamethodAll);
                }

                httpmethod2javamethodAll.putAll(httpmethod2javamethod);
            }
        }

        return url2httpmethod2javamethodAll;
    }

    /**
     * 根据接口类的注解生成API映射表
     * 
     * @param interfaceClass 接口类
     * @return 映射表
     */
    public static Map<String, Map<String, Method>> buildApiMappingByInterfaceAnno(Class<?> interfaceClass) {
        Map<String, Map<String, Method>> url2httpmethod2javamethod = new HashMap<String, Map<String, Method>>();

        // for (Class<?> apiIf : interfaces) {
        // API接口:类级别是否有Path注解
        String apiIfPath = null;
        if (interfaceClass.isAnnotationPresent(Path.class)) {
            Path pathAnno = interfaceClass.getAnnotation(Path.class);
            apiIfPath = pathAnno.value();
        }
        if (apiIfPath == null) {
            return url2httpmethod2javamethod;
        }

        // API接口：函数级别是否有注解
        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            // 字段上是否有Path类型的注解
            if (method.isAnnotationPresent(Path.class)) {
                Path pathAnno = method.getAnnotation(Path.class);
                String value = pathAnno.value();

                String url = apiIfPath + value;
                if (!url2httpmethod2javamethod.containsKey(url)) {
                    url2httpmethod2javamethod.put(url, new HashMap<String, Method>());
                }
                Map<String, Method> httpmethod2javamethod = url2httpmethod2javamethod.get(url);

                if (method.isAnnotationPresent(GET.class)) {
                    httpmethod2javamethod.put(GET.class.getSimpleName(), method);
                }
                if (method.isAnnotationPresent(PUT.class)) {
                    httpmethod2javamethod.put(PUT.class.getSimpleName(), method);
                }
                if (method.isAnnotationPresent(POST.class)) {
                    httpmethod2javamethod.put(POST.class.getSimpleName(), method);
                }
                if (method.isAnnotationPresent(DELETE.class)) {
                    httpmethod2javamethod.put(DELETE.class.getSimpleName(), method);
                }
            }
        }

        return url2httpmethod2javamethod;
    }
}
