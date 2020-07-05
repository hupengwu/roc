/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 比较工具
 * 
 * @author h00442047
 * @since 2019年12月28日
 */
public class CompareUtil {
    private static CompareUtil instance = new CompareUtil();

    /**
     * 实例化
     * 
     * @return 实例化对象
     */
    public static CompareUtil getInstance() {
        return instance;
    }

    /**
     * 比较两个列表是否一致
     * 
     * @param srcList
     * @param dstList
     * @return
     */
    public <T> boolean compareByValue(List<T> srcList, List<T> dstList) {
        List<T> addList = new ArrayList<T>();
        List<T> delList = new ArrayList<T>();
        compareA2BByValue(srcList, dstList, addList);
        compareA2BByValue(dstList, srcList, delList);

        return addList.isEmpty() && delList.isEmpty();
    }

    /**
     * 以A为基准进行比较：B即转换为A，需要进行的增删操作
     * 
     * @param srcList 基准列表
     * @param dstList 比对对象
     * @param addList 比对对象相对基准表，需要增加的数据部分
     * @param delList 比对对象相对基准表，需要减少的数据部分
     */
    public <T> void compareByValue(List<T> srcList, List<T> dstList, List<T> addList, List<T> delList) {
        compareA2BByValue(srcList, dstList, addList);
        compareA2BByValue(dstList, srcList, delList);
    }

    /**
     * 以A为基准进行比较
     * 
     * @param srcList 基准列表
     * @param dstList 比对对象
     * @param addList 比对对象相对基准表，需要增加的数据部分
     */
    private <T> void compareA2BByValue(List<T> srcList, List<T> dstList, List<T> addList) {
        for (T aT : dstList) {
            // 检查：是否存在
            boolean isexist = false;
            for (T bT : srcList) {
                if (aT.equals(bT)) {
                    isexist = true;
                    break;
                }
            }

            if (!isexist) {
                addList.add(aT);
            }
        }
    }

    /**
     * 比较A/B是否数值完全相同
     * 
     * @param aList a集合
     * @param bList b集合
     * @return 是否一致
     */
    public <T> boolean compareByValue(Set<T> aList, Set<T> bList) {
        Set<T> addList = new HashSet<T>();
        Set<T> delList = new HashSet<T>();
        Set<T> eqlList = new HashSet<T>();
        compareByValue(aList, bList, addList, delList, eqlList);

        return (addList.isEmpty() && delList.isEmpty());
    }

    /**
     * 以DST为基准进行比较：SRC即转换为DST，对SRC需要进行的增删操作
     * 
     * @param srcList
     * @param dstList
     * @param addList
     * @param delList
     */
    public <T> void compareByValue(Set<T> srcList, Set<T> dstList, Set<T> addList, Set<T> delList, Set<T> eqlList) {
        compareA2BByValue(srcList, dstList, addList, eqlList);
        compareA2BByValue(dstList, srcList, delList, eqlList);
    }

    /**
     * 以DST为基准进行比较，需要对SRC进行修改的动作
     * @param srcList
     * @param dstList
     * @param addList
     * @param eqlList
     */
    private <T> void compareA2BByValue(Set<T> srcList, Set<T> dstList, Set<T> addList, Set<T> eqlList) {
        for (T dstT : dstList) {
            if (null != dstT && srcList.contains(dstT)) {
                eqlList.add(dstT);
            } else {
                addList.add(dstT);
            }
        }
    }

    /**
     * 比较两个列表是否完全一致：不但比较A/B列表的数值一致，还比较双方数值顺序的一致性
     * 
     * @param aList
     * @param bList
     * @return
     */
    public <T> boolean compareA2BByConsistency(List<T> aList, List<T> bList) {
        int size = aList.size();
        if (bList.size() != size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!aList.get(i).equals(bList.get(i))) {
                return false;
            }
        }

        return true;
    }
}
