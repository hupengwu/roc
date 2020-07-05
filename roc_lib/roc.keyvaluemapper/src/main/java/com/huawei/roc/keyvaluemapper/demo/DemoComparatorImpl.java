
package com.huawei.roc.keyvaluemapper.demo;

import java.util.HashMap;
import java.util.Map;

import com.huawei.roc.keyvaluemapper.Readers;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;

public class DemoComparatorImpl extends Readers {

    private String result = null;

    public boolean readValue(Object elemtKey, Object elemtValue) {
        Map<Long, String> value = (Map<Long, String>) elemtValue;
        String rr = value.get(1L);

        if (value.containsKey(9999999L)) {
            result = elemtValue.toString();
            return true;
        }

        return true;
    }

    public static void main(String[] vrg) {

        long start = System.currentTimeMillis();

        KeyValueMapper mapper = new KeyValueMapper();
        for (Long key = 0L; key < 1000000; key++) {
            Map<Long, String> value = new HashMap<Long, String>();
            value.put(key, key.toString());

            mapper.put(key, value);
        }

        long end = System.currentTimeMillis();
        long span = end - start;

        start = System.currentTimeMillis();

        int count = 0;

        Readers comparator = new DemoComparatorImpl();
        try {
            // mapper.findByComparator(comparator);
            count = mapper.foreach(comparator);
        } catch (Exception e) {
            e.toString();
        }

        end = System.currentTimeMillis();
        span = end - start;

        return;
    }
}
