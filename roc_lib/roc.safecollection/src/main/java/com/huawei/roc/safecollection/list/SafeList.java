
package com.huawei.roc.safecollection.list;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author h00442047
 * @since 2020年1月11日
 * @param <E>
 */
public abstract class SafeList<E> {
    private List<E> data;

    public SafeList(List<E> data) {
        this.data = data;
    }

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

    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        return this.data.addAll(index, c);
    }

    public synchronized boolean removeAll(Collection<?> c) {
        return this.data.removeAll(c);
    }

    public synchronized void clear() {
        this.data.clear();
    }

    public synchronized E get(int index) {
        return this.data.get(index);
    }

    public synchronized E set(int index, E element) {
        return this.data.set(index, element);
    }

    public synchronized void add(int index, E element) {
        this.data.add(index, element);
    }

    public synchronized E remove(int index) {
        return this.data.remove(index);
    }

    public synchronized int indexOf(Object o) {
        return this.data.indexOf(o);
    }

    public synchronized int lastIndexOf(Object o) {
        return this.data.lastIndexOf(o);
    }

    public synchronized List<E> subList(int fromIndex, int toIndex) {
        return this.data.subList(fromIndex, toIndex);
    }

    /**
     * 复制一个非安全的HashSet副本
     * 
     * @return
     */
    public synchronized List<E> duplicate(List<E> result) {
        result.addAll(this.data);
        return result;
    }

    /**
     * 遍历读数据
     * 
     * @param reader 阅读者
     */
    public synchronized void forEach(ListReader<E> reader) {
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