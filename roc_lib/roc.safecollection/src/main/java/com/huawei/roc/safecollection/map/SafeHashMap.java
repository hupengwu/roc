
package com.huawei.roc.safecollection.map;

import java.util.HashMap;

public class SafeHashMap<K, V> extends SafeMap<K, V> {
    public SafeHashMap() {
        super(new HashMap<K, V>());
    }
}
