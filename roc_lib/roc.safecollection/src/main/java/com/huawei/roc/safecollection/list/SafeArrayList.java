
package com.huawei.roc.safecollection.list;

import java.util.ArrayList;

public class SafeArrayList<E> extends SafeList<E> {
    public SafeArrayList() {
        super(new ArrayList<E>());
    }
}
