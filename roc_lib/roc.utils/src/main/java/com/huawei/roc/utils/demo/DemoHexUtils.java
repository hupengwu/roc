
package com.huawei.roc.utils.demo;

import com.huawei.roc.utils.HexUtils;

public class DemoHexUtils {

    public static void main(String[] args) {

        byte[] data = HexUtils.hexStringToByteArray(
            "1234567890ABCEDF");

        long time = System.currentTimeMillis();

        String s = null;
        for (int i = 0; i < 10000000; i++) {
            // s = ArrayUtils.byteArrayToHexString(data);
        }

        long s1 = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            s = HexUtils.byteArrayToHexString(data);
        }

        long s2 = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            // s = ArrayUtils.toHexString(data);
        }

        long s3 = System.currentTimeMillis() - time;
        time = System.currentTimeMillis();

    }

}