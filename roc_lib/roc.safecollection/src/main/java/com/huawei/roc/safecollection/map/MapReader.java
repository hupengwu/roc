
package com.huawei.roc.safecollection.map;

/**
 * 遍历器（读操作）：遍历所有的元素
 * 说明：该操作涉及到多线程，所以哈希表中的value，不能带出这个接口的外部去访问，否则会出现多线程安全问题
 * 
 * @author h00442047
 * @since 2019年11月23日
 */
public abstract class MapReader<K, V> {
    /**
     * 遍历读操作
     * @param data 元素
     */
    abstract public void read(final K key, final V value);
}
