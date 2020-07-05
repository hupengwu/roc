
package com.huawei.roc.protocol.demo;

import com.huawei.roc.protocol.modbus.ModBusCmd;
import com.huawei.roc.protocol.modbus.def.Mode;
import com.huawei.roc.utils.CRC16Utils;
import com.huawei.roc.utils.HexUtils;
import com.huawei.roc.value.ByteArrayValue;
import com.huawei.roc.value.ByteValue;
import com.huawei.roc.value.ShortArrayValue;
import com.huawei.roc.value.ShortValue;

public class DemoModBusCmd {
    public static void main(String[] args) throws Exception {

        byte[] arrData = {0x00, 0x13, 0x00, 0x25};
        ByteArrayValue arrCmdValue = new ByteArrayValue();

        byte[] aacmd = HexUtils.hexStringToByteArray("0106000100179804");
        byte[] aacmd1 = HexUtils.hexStringToByteArray("01 03 04 00 00 00 00 fa 33");
        int crc = 0;
        crc = ModBusCmd.getCRC16XOR(aacmd1, aacmd1.length, 0xffff, 0xa001, 0x0000);

        ModBusCmd.UnPackCmdReadHoldingRegisters(aacmd1, new ByteValue(), new ShortArrayValue(), Mode.RTU);

        ModBusCmd.UnPackCmdRTU(aacmd, new ByteValue(), new ByteValue(), new ByteArrayValue());

        ModBusCmd.PackCmdRTU((byte) 0x11, (byte) 0x01, arrData, arrCmdValue);
        ModBusCmd.UnPackCmdRTU(arrCmdValue.getValue(), new ByteValue(), new ByteValue(), new ByteArrayValue());

        // Slave Address 11
        // Function 01
        // Starting Address Hi 00
        // Starting Address Lo 13
        // No. of Points Hi 00
        // No. of Points Lo 25
        ModBusCmd.PackCmdASCII((byte) 0x11, (byte) 0x01, arrData, arrCmdValue);

        ModBusCmd.UnPackCmdASCII(arrCmdValue.getValue(), new ByteValue(), new ByteValue(), new ByteArrayValue());

        ModBusCmd.PackCmdReadHoldingRegisters((byte) 0x01, (byte) 0x0004, (short) 0x0002, arrCmdValue, Mode.RTU);

        String result = HexUtils.byteArrayToHexString(arrCmdValue.getValue());

        arrCmdValue.setValue(
            new byte[] {0x11, 0x01, 0x05, (byte) 0xcd, 0x6b, (byte) 0xb2, 0x0e, 0x1b, (byte) 0xf0, (byte) 0x00});
        ModBusCmd.UnPackCmdReadCoilStatus(arrCmdValue
            .getValue(), new ByteValue(), new ShortValue(), new ByteArrayValue(), Mode.RTU);
    }
}
