/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    private static ListUtils instance = new ListUtils();

    public static ListUtils getInstance() {
        return instance;
    }

    /**
     * 从A类型列表转换成B类型列表:A/B是派生类关系
     * @param aClazzList
     * @param bClazz
     * @return
     */
    public <A, B> List<B> convertClassList(List<A> aClazzList, Class<B> bClazz) {
        List<B> bInstanceList = new ArrayList<B>();

        for (A aInstance : aClazzList) {
            if (bClazz.isInstance(aInstance)) {
                bInstanceList.add(bClazz.cast(aInstance));
            }
        }

        return bInstanceList;
    }

    /**
     * 将列表进行分页分割，变成多个列表。网络常用于分批下发
     * @param datList
     * @param page
     * @return
     */
    public <T> List<List<T>> splitList(List<T> datList, int page) {
        List<List<T>> resultList = new ArrayList<List<T>>();
        List<T> tempList = new ArrayList<T>();

        for (int index = 0; index < datList.size(); index++) {
            tempList.add(datList.get(index));

            if ((tempList.size() % page == 0) || (index == datList.size() - 1)) {
                resultList.add(tempList);
                tempList = new ArrayList<T>();
            }
        }

        return resultList;
    }
}
