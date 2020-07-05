
package com.huawei.roc.safecollection.map;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class SafeMap<K, V> {
    private Map<K, V> data;

    public SafeMap(Map<K, V> data) {
        this.data = data;
    }

    public synchronized int size() {
        return this.data.size();
    }

    public synchronized boolean isEmpty() {
        return this.data.isEmpty();
    }

    public synchronized V get(Object key) {
        return this.data.get(key);
    }

    public synchronized boolean containsKey(Object key) {
        return this.data.containsKey(key);
    }

    public synchronized V put(K key, V value) {
        return this.data.put(key, value);
    }

    public synchronized void putAll(Map<? extends K, ? extends V> m) {
        this.data.putAll(m);
    }

    public synchronized V remove(Object key) {
        return this.data.remove(key);
    }

    public synchronized void clear() {
        this.data.clear();
    }

    public synchronized boolean containsValue(Object value) {
        return this.data.containsValue(value);
    }

    public synchronized Set<K> keySet() {
        return this.data.keySet();
    }

    public synchronized Collection<V> values() {
        return this.data.values();
    }

    /**
     * 复制一个非安全的HashSet副本
     * 
     * @return
     */
    public synchronized Map<K, V> duplicate(Map<K, V> result) {
        result.putAll(this.data);
        return result;
    }

    /**
     * 遍历读数据
     * 
     * @param reader 阅读者
     */
    public synchronized void forEach(MapReader<K, V> reader) {
        for (Entry<K, V> entry : this.data.entrySet()) {
            reader.read(entry.getKey(), entry.getValue());
        }
    }

    private synchronized String toStringFunc() {
        return this.data.toString();
    }

    @Override
    public String toString() {
        return this.toStringFunc();
    }
}
