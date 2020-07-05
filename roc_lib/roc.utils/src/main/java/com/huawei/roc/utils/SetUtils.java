/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetUtils {
    /**
     * Set转换成List
     * @param datList Set类型
     * @return List类型
     */
    public static <T> List<T> set2List(Set<T> datList) {
        List<T> datHash = new ArrayList<T>(datList.size());
        for (T dat : datList) {
            datHash.add(dat);
        }

        return datHash;
    }

    /**
     * List转换成Set
     * @param datList List类型
     * @return Set类型
     */
    public static <T> Set<T> list2Set(List<T> datList) {
        Set<T> datHash = new HashSet<T>(datList.size());
        for (T dat : datList) {
            datHash.add(dat);
        }

        return datHash;
    }
}
