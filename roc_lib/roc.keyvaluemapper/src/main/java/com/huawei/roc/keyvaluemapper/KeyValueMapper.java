/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.keyvaluemapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于哈希表的KV缓存表，它是线程安全的对象 
 * 
 * @author hupengwu
 * @since 2019/9/20
 *
 */
public class KeyValueMapper {
    /** 
     * 读写锁
     */
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /** 
     * 数据存储：哈希表 
     */
    private Map<Object, Object> key2value = new HashMap<Object, Object>();

    /**
     * 数量大小
     * 
     * @return 元素的数量
     */
    public int size() {
        try {
            lock.writeLock().lock();
            return this.key2value.size();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 插入数据
     * 
     * @param key key
     * @param value value
     */
    public void put(Object key, Object value) {
        try {
            lock.writeLock().lock();
            this.key2value.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 插入数据
     * 
     * @param m 哈希表
     */
    public void putAll(Map<Object, Object> m) {
        try {
            lock.writeLock().lock();
            this.key2value.putAll(m);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 删除数据
     * 
     * @param key 一个对象的key
     */
    public Object remove(Object key) {
        try {
            lock.writeLock().lock();
            return this.key2value.remove(key);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 删除指定的对象
     * 
     * @param keys 一组对象的key
     * @throws Exception 异常
     */
    public void remove(Set<Object> keys) throws Exception {
        try {
            lock.writeLock().lock();

            for (Object key : keys) {
                this.key2value.remove(key);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 清空数据
     */
    public void clear() {
        try {
            lock.writeLock().lock();
            this.key2value.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 是否包含数据
     * 
     * @param key key
     */
    public <K> boolean containsKey(K key) {
        try {
            lock.readLock().lock();
            return this.key2value.containsKey(key);
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 复制一个非安全的HashSet副本
     * 
     * @return
     */
    public synchronized Set<Object> copyKeySet() {
        Set<Object> result = new HashSet<Object>();
        result.addAll(this.key2value.keySet());
        return result;
    }

    /**
     * 全遍历：读数据 </br>
     * 支持多线程的并行读操作
     * 
     * @param reader 读类型遍历器
     * @return 数量
     * @throws Exception 异常信息
     */
    public int foreach(Readers reader) throws Exception {
        int count = 0;
        try {
            lock.readLock().lock();

            for (Entry<Object, Object> entry : this.key2value.entrySet()) {
                if (reader.readValue(entry.getKey(), entry.getValue())) {
                    count++;
                }
            }

        } finally {
            lock.readLock().unlock();
        }
        return count;
    }

    /**
     * 全遍历：写数据 </br>
     * 支持多线程下的串行写操作
     * 
     * @param writer 写遍历器
     * @return 命中的数量
     * @throws Exception 异常信息
     */
    public int foreach(Writers writer) throws Exception {
        int count = 0;
        try {
            lock.writeLock().lock();

            for (Entry<Object, Object> entry : this.key2value.entrySet()) {
                if (writer.writeValue(entry.getKey(), entry.getValue())) {
                    count++;
                }
            }

        } finally {
            lock.writeLock().unlock();
        }
        return count;
    }

    /**
     * 更新指定的数据
     * 
     * @param writer 写遍历器
     * @throws Exception
     */
    public boolean update(Writer writer) throws Exception {
        try {
            lock.writeLock().lock();
            Object value = this.key2value.get(writer.getKey());
            if (value == null) {
                return false;
            }
            return writer.writeValue(value);

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 读取指定的数据
     * 
     * @param updater 写遍历器
     * @throws Exception
     */
    public boolean read(Reader reader) throws Exception {
        try {
            lock.readLock().lock();
            Object value = this.key2value.get(reader.getKey());
            if (value == null) {
                return false;
            }
            return reader.readValue(value);

        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 遍历限定范围：写数据
     * 
     * @param keys 一组对象的key
     * @param writer 写遍历器
     * @throws Exception
     */
    public void foreach(Set<Object> keys, Writers writer) throws Exception {
        try {
            lock.writeLock().lock();

            for (Object key : keys) {
                Object value = this.key2value.get(key);
                if (value == null) {
                    continue;
                }
                writer.writeValue(key, value);
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 遍历限定范围：读数据
     * 
     * @param keys 一组对象的key
     * @param reader 读遍历器
     * @throws Exception
     */
    public void foreach(Set<Object> keys, Readers reader) throws Exception {
        try {
            lock.writeLock().lock();

            for (Object key : keys) {
                Object value = this.key2value.get(key);
                if (value == null) {
                    continue;
                }
                reader.readValue(key, value);
            }

        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * 查找指定条件的某个对象
     * 
     * @param reader 遍历器
     * @throws Exception
     */
    public void find(Readers reader) throws Exception {
        try {
            lock.readLock().lock();

            for (Entry<Object, Object> entry : this.key2value.entrySet()) {
                if (reader.readValue(entry.getKey(), entry.getValue())) {
                    return;
                }
            }

        } finally {
            lock.readLock().unlock();
        }
    }

}
