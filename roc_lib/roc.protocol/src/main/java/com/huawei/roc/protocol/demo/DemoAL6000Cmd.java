
package com.huawei.roc.protocol.demo;

import com.huawei.roc.protocol.al6000.AL6000Cmd;
import com.huawei.roc.utils.HexUtils;
import com.huawei.roc.value.ByteArrayValue;
import com.huawei.roc.value.ByteValue;
import com.huawei.roc.value.IntegerValue;

public class DemoAL6000Cmd {

    public static void main(String[] args) throws Exception {
        byte[] arrCmd =
            HexUtils.hexStringToByteArray("02 30 30 31 30 34 30 30 30 30 43 FF 00 00 80 00 80 00 00 00 00 0C 60 03 E6");

        ByteValue wMId = new ByteValue();
        ByteValue byMsgty = new ByteValue();
        IntegerValue dwAddr = new IntegerValue();
        ByteArrayValue arrData = new ByteArrayValue();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            AL6000Cmd.UnPackCmdComli0(arrCmd, wMId, byMsgty, dwAddr, arrData);
        }
        long t2 = System.currentTimeMillis();
        long t3 = t2 - t1;
        byte i = -125;
        byte j = 125;
        i = (byte) (i - j);
        i = 0;
    }
}
