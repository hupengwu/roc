/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Map工具集合
 * 
 * @author h00442047
 * @since 2019/11/13
 */
public class MapUtils {
    /**
     * 交换Key-Value
     * 
     * @param key2value key2value
     * @return value2key
     */
    public static <K, V> Map<V, K> exchange(Map<K, V> key2value) {
        Map<V, K> result = new HashMap<V, K>(key2value.size());

        for (Entry<K, V> entry : key2value.entrySet()) {
            result.put(entry.getValue(), entry.getKey());
        }

        return result;
    }

    /**
     * 根据Key提取出相关的值列表
     * 
     * @param key2value key2value
     * @param keyList key列表
     * @return 跟key相关的values
     */
    public static <K, V> List<V> getValueListByKey(Map<K, V> key2value, Collection<K> keyList) {
        List<V> resultList = new ArrayList<V>();
        for (K key : keyList) {
            V value = key2value.get(key);
            if (value != null) {
                resultList.add(value);
            }
        }

        return resultList;
    }
}
