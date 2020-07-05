
package com.huawei.roc.utils.demo;

import java.util.ArrayList;
import java.util.List;

import com.huawei.roc.utils.EscapeUtils;
import com.huawei.roc.utils.HexUtils;

public class DemoEscapeUtils {
    public static void main(String[] args) {
        Integer ss1 = Integer.valueOf("ff", 16);
        byte bb = ss1.byteValue();

        String sss = EscapeUtils.escapeOneChar("12334567890", "3", "33");
        sss = EscapeUtils.unescapeOneChar(sss, "3", "33");

        byte[] array = HexUtils.hexStringToByteArray(
            "122356fffafb122356fffafb122356fffafb122356fffafb122356fffafb122356fffafb122356fffafb");

        // 控制符
        byte esc = Integer.valueOf("FF", 16).byteValue();
        byte begin = Integer.valueOf("fa", 16).byteValue();
        byte end = Integer.valueOf("fb", 16).byteValue();

        // 冲突字符
        byte escEsc = Integer.valueOf("00", 16).byteValue();
        byte escBegin = Integer.valueOf("01", 16).byteValue();
        byte escEnd = Integer.valueOf("02", 16).byteValue();

        byte[] outs1 = EscapeUtils.escapeByteArray("{\"clientId\":\"clinet-01\"}".getBytes(), esc, begin, end, escEsc, escBegin, escEnd);
        String ssw = HexUtils.byteArrayToHexString(outs1);

        byte[] outs = EscapeUtils.escapeByteArray(array, esc, begin, end, escEsc, escBegin, escEnd);

        List<byte[]> inputs = new ArrayList<byte[]>();
        inputs.add(new byte[100000]);
        inputs.add(new byte[100000]);
        outs[outs.length - 1] = 0;
        inputs.add(outs);
        for (int i = 0; i < 300 * 1024; i++) {
            inputs.add(new byte[1024]);
        }
        inputs.add(new byte[] {end});
        inputs.add(new byte[100000]);

        long t1 = System.currentTimeMillis();
        outs = EscapeUtils.unescapeByteArray(inputs, esc, begin, end, escEsc, escBegin, escEnd);
        long t2 = System.currentTimeMillis();
        long t3 = t2 - t1;

        // for (int i = 0; i < 10000; i++) {
        // byte[] outs = EscapeUtils.escapeByteArray(array, esc, begin, end, escEsc, escBegin, escEnd);
        // array = EscapeUtils.unescapeByteArray(outs, esc, begin, end, escEsc, escBegin, escEnd);
        // }
        int i = 0;
    }
}
