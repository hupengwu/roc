
package com.huawei.roc.safecollection.set;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 多线程安全的HashSet：不包含有遍历器
 * 
 * @author h00442047
 * @since 2019年12月27日
 * @param <E>
 */
public class SafeHashSet<E> {
    private Set<E> data = new HashSet<E>();

    public synchronized int size() {
        return this.data.size();
    }

    public synchronized boolean isEmpty() {
        return this.data.isEmpty();
    }

    public synchronized boolean contains(Object o) {
        return this.data.contains(o);
    }

    public synchronized Object[] toArray() {
        return this.data.toArray();
    }

    public synchronized <T> T[] toArray(T[] a) {
        return this.data.toArray(a);
    }

    public synchronized boolean add(E e) {
        return this.data.add(e);
    }

    public synchronized boolean remove(Object o) {
        return this.data.remove(o);
    }

    public synchronized boolean containsAll(Collection<?> c) {
        return this.data.containsAll(c);
    }

    public synchronized boolean addAll(Collection<? extends E> c) {
        return this.data.addAll(c);
    }

    public synchronized boolean retainAll(Collection<?> c) {
        return this.data.retainAll(c);
    }

    public synchronized boolean removeAll(Collection<?> c) {
        return this.data.removeAll(c);
    }

    public synchronized void clear() {
        this.data.clear();
    }

    /**
     * 复制一个非安全的HashSet副本
     * 
     * @return
     */
    public synchronized Set<E> duplicate(Set<E> result) {
        result.addAll(this.data);
        return result;
    }

    /**
     * 遍历读数据
     * 
     * @param reader 阅读者
     */
    public synchronized void forEach(SetReader<E> reader) {
        for (E e : this.data) {
            reader.read(e);
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
