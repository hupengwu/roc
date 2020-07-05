
package com.huawei.roc.keyvaluemapper.demo;

import java.util.HashMap;
import java.util.Map;

import com.huawei.roc.keyvaluemapper.Readers;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;

/**
 * 根据值生成索引:这是如何通过遍历快速生成索引的例子
 * @author hupengwu
 *
 */
public class Value2KeyComparator extends Readers {

    private Map<Object, Object> value2key = new HashMap<Object, Object>();

    public boolean readValue(Object elemtKey, Object elemtValue) {
        value2key.put(elemtValue, elemtKey);
        return true;
    }

    public Map<Object, Object> getValue2key() {
        return value2key;
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

        Value2KeyComparator comparator = new Value2KeyComparator();
        try {
            // mapper.findByComparator(comparator);
            count = mapper.foreach(comparator);
        } catch (Exception e) {
            e.toString();
        }

        end = System.currentTimeMillis();
        span = end - start;

        comparator.getValue2key();

        return;
    }
}
