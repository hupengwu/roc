
package com.huawei.roc.utils.demo;

import java.util.ArrayList;
import java.util.List;

import com.huawei.roc.utils.KeyUtils;

public class DemoKeyUtils {
    public static void main(String[] args) {
        List<Integer> datList = new ArrayList<Integer>();
        for (int i = 0; i < 10000000; i++) {
            datList.add(i);
        }

        List<Integer> ids = KeyUtils.getIDListByIntegerKey(datList, "intValue");
        ids = null;
    }
}
