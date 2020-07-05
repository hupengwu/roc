
package com.huawei.roc.safecollection.list;

import java.util.LinkedList;

public class SafeLinkedList<E> extends SafeList<E> {
    public SafeLinkedList() {
        super(new LinkedList<E>());
    }
}
