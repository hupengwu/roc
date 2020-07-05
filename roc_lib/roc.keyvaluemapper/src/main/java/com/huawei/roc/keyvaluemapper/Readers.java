/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.keyvaluemapper;

/**
 * 遍历器（读操作）：遍历所有的元素
 * 说明：该操作涉及到多线程，所以哈希表中的value，不能带出这个接口的外部去访问，否则会出现多线程安全问题
 * 
 * @author h00442047
 * @since 2019年11月23日
 */
public abstract class Readers {
    /**
     * 比较该对象是否为目标数据
     * 
     * @param key 元素的Key
     * @param value 元素
     * @return 是否为目标数据
     */
    abstract public boolean readValue(Object key, Object value);
}
