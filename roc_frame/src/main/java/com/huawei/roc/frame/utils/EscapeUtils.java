
package com.huawei.roc.frame.utils;

import com.huawei.roc.frame.constant.Escape;

public class EscapeUtils {
    public static byte[] escapeByteArray(String input) {
        return escapeByteArray(input.getBytes());
    }

    public static byte[] escapeByteArray(byte[] input) {
        return com.huawei.roc.utils.EscapeUtils.escapeByteArray(input,
            Escape.esc,
            Escape.begin,
            Escape.end,
            Escape.escEsc,
            Escape.escBegin,
            Escape.escEnd);
    }
}
