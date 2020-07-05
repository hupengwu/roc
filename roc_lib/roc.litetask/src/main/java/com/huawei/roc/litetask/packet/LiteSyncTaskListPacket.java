/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.litetask.packet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 轻量级的多线程任务包:使用多线程并行处理来解决一批任务的高强度计算的问题<br>
 * 多线程任务包：任务包裹用于放置多线程处理的输入/输出数据<br>
 * 说明：相对父类，新增了带回结果的方法
 * @author h00163887
 * @since 2019/09/21
 */
public abstract class LiteSyncTaskListPacket extends LiteSyncTaskPacket {
    /**
     * 处理后的结果
     */
    private List<Object> results = new LinkedList<Object>();

    /**
     * 把处理结果保存起来：用于保存对象型的结果
     * @param e 结果
     */
    public synchronized <RESULT> void addResult(RESULT e) {
        this.results.add(e);
    }

    /**
     * 把处理结果保存起来:用于保存数组型的结果
     * @param e 结果
     */
    public synchronized <RESULT> void addAllResult(Collection<RESULT> e) {
        this.results.addAll(e);
    }

    /**
     * 清空缓存
     */
    public synchronized void clearResult() {
        this.results.clear();
    }

    /**
     * 取出任务结果
     * @param clazz 类型
     * @return 结果集
     */
    public synchronized <RESULT> List<RESULT> getResultList(Class<RESULT> clazz) {
        List<RESULT> temps = new ArrayList<RESULT>();
        for (Object temp : results) {
            if (!clazz.isInstance(temp)) {
                return null;
            }

            temps.add(clazz.cast(temp));

        }
        return temps;
    }
}