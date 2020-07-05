
package com.huawei.roc.restlike.demo;

import java.util.Map;

public class IDemoImpl implements IDemoApi {
    @Override
    public void apiFunc1(Integer a, String b, Integer c) {
        System.out.println("apiFunc1");
        System.out.println(b);
        System.out.println(c);
    }

    @Override
    public void apiFunc2(Integer a, String b) {
        System.out.println("apiFunc2");
    }

    @Override
    public void apiFunc3(Integer a, Map<String, Object> param) {
        System.out.println("apiFunc3");
    }

}
