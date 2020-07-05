/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.litetask.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 轻量级的多线程任务包:使用多线程并行处理来解决一批任务的高强度计算的问题<br>
 * 多线程任务包：任务包裹用于放置多线程处理的输入/输出数据<br>
 *  说明：相对父类，新增了带回结果的方法
 * @author h00163887
 * @since 2019/09/21
 */
public abstract class LiteSyncTaskMapPacket extends LiteSyncTaskPacket {
    /**
     * 处理后的结果
     */
    private Map<Object, Object> results = new HashMap<Object, Object>();

    /**
     * 把处理结果保存起来：用于保存对象型的结果
     * @param key 索引
     * @param value 结果值
     */
    public synchronized <KEY, VALUE> void addResult(KEY key, VALUE value) {
        this.results.put(key, value);
    }

    /**
     * 清空缓存
     */
    public synchronized void clearResult() {
        this.results.clear();
    }

    /**
     * 取出任务结果
     * @param keyClazz 索引的对象类型
     * @param valueClazz 值的对象类型
     * @return 结果集合
     */
    public synchronized <KEY, VALUE> Map<KEY, VALUE> getResults(Class<KEY> keyClazz, Class<VALUE> valueClazz) {
        Map<KEY, VALUE> temps = new HashMap<KEY, VALUE>();

        for (Entry<Object, Object> entry : results.entrySet()) {
            if (!keyClazz.isInstance(entry.getKey())) {
                return null;
            }
            KEY key = keyClazz.cast(entry.getKey());

            if (!valueClazz.isInstance(entry.getValue())) {
                return null;
            }
            VALUE value = valueClazz.cast(entry.getValue());

            temps.put(key, value);
        }

        return temps;
    }
}
