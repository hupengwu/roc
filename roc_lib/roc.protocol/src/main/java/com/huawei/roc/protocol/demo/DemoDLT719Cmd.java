
package com.huawei.roc.protocol.demo;

import com.huawei.roc.protocol.dlt719.Dlt719Cmd;
import com.huawei.roc.utils.HexUtils;
import com.huawei.roc.value.ByteArrayValue;
import com.huawei.roc.value.ByteValue;
import com.huawei.roc.value.ShortValue;

public class DemoDLT719Cmd {
    public static void main(String[] args) throws Exception {

        byte[] arrLPDU =
            HexUtils.hexStringToByteArray("68 10 10 68 08 01 00 8D 01 31 00 00 00 01 30 30 30 30 30 31 EA 16");
        ByteValue byCtrlValue = new ByteValue();
        ShortValue wAddrValue = new ShortValue();
        ByteArrayValue arrASDUValue = new ByteArrayValue();
        ByteArrayValue arrLPDUValue = new ByteArrayValue();
        Dlt719Cmd.unpackLPDUVL(arrLPDU, byCtrlValue, wAddrValue, arrASDUValue);
        Dlt719Cmd.packLPDUVL(byCtrlValue.getValue(), wAddrValue.getValue(), arrASDUValue.getValue(), arrLPDUValue);
        String result = HexUtils.byteArrayToHexString(arrLPDUValue.getValue());
    }
}
