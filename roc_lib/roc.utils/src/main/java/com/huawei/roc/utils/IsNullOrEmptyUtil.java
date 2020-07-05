/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 检查是否为空
 * 
 * @author h00442047
 * @since 2019年12月20日
 */
public final class IsNullOrEmptyUtil {
    private IsNullOrEmptyUtil() {
    }

    /**
     * 检查是否为空
     * 
     * @param param 字符串
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(String param) {
        if (param == null || param.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 数组为空或者为空心的
     * 
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isNullOrEmpty(T[] array) {
        if (array == null || array.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 集合为空或者空心的
     * 
     * @param collection map
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * 集合为空或者空心的
     * 
     * @param collection 集合
     * @return 是否为空
     */
    public static boolean isNullOrEmpty(Collection<? extends Object> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param params
     * @return
     */
    public static boolean hasNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                return true;
            }
        }

        return false;
    }

}
