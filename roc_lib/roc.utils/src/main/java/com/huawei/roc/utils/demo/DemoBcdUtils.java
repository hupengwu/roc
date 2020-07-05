
package com.huawei.roc.utils.demo;

import java.util.ArrayList;
import java.util.List;

import com.huawei.roc.utils.BcdUtils;
import com.huawei.roc.utils.KeyUtils;

public class DemoBcdUtils {
    public static void main(String[] args) {
        byte[] bcd = BcdUtils.str2bcd("63");
        List<Integer> datList = new ArrayList<Integer>();
        for (int i = 0; i < 10000000; i++) {
            datList.add(i);
        }

        List<Integer> ids = KeyUtils.getIDListByIntegerKey(datList, "intValue");
        ids = null;
    }
}
