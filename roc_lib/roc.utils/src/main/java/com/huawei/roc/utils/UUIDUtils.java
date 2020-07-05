
package com.huawei.roc.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 生成去掉-字符的UUID字符串
     * 
     * @return 去掉-字符的UUID字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
