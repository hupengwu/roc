
package com.huawei.roc.protocol.al6000;

import com.huawei.roc.value.ByteArrayValue;
import com.huawei.roc.value.ByteValue;
import com.huawei.roc.value.IntegerValue;

/**
 * 
 * @author h00442047
 *
 */
public class AL6000Cmd {

    /**
     * 二进制按位逆序，如15h变成A8h
     * @param byNo
     * @return
     */
    public static byte NegativeSequence(byte byNo) {
        byte byNewNo = 0x00;
        for (int i = 0; i < 8; i++) {
            byNewNo = (byte) (byNewNo << 1);
            byNewNo += (byNo >> i) & 0x01;
        }

        return byNewNo;
    }

    public static void HexToAscii(byte byAt, ByteValue chAsciiHOut, ByteValue chAsciiLOut) {
        byte chAsciiH = 0x00;
        byte chAsciiL = 0x00;

        switch (byAt & 0xF0) {
            case 0x00:
                chAsciiH = 0x30;
                break;
            case 0x10:
                chAsciiH = 0x31;
                break;
            case 0x20:
                chAsciiH = 0x32;
                break;
            case 0x30:
                chAsciiH = 0x33;
                break;
            case 0x40:
                chAsciiH = 0x34;
                break;
            case 0x50:
                chAsciiH = 0x35;
                break;
            case 0x60:
                chAsciiH = 0x36;
                break;
            case 0x70:
                chAsciiH = 0x37;
                break;
            case 0x80:
                chAsciiH = 0x38;
                break;
            case 0x90:
                chAsciiH = 0x39;
                break;
            case 0xA0:
                chAsciiH = 0x41;
                break;
            case 0xB0:
                chAsciiH = 0x42;
                break;
            case 0xC0:
                chAsciiH = 0x43;
                break;
            case 0xD0:
                chAsciiH = 0x44;
                break;
            case 0xE0:
                chAsciiH = 0x45;
                break;
            case 0xF0:
                chAsciiH = 0x46;
                break;
            default:
                return;
        }

        switch (byAt & 0x0F) {
            case 0x00:
                chAsciiL = 0x30;
                break;
            case 0x01:
                chAsciiL = 0x31;
                break;
            case 0x02:
                chAsciiL = 0x32;
                break;
            case 0x03:
                chAsciiL = 0x33;
                break;
            case 0x04:
                chAsciiL = 0x34;
                break;
            case 0x05:
                chAsciiL = 0x35;
                break;
            case 0x06:
                chAsciiL = 0x36;
                break;
            case 0x07:
                chAsciiL = 0x37;
                break;
            case 0x08:
                chAsciiL = 0x38;
                break;
            case 0x09:
                chAsciiL = 0x39;
                break;
            case 0x0A:
                chAsciiL = 0x41;
                break;
            case 0x0B:
                chAsciiL = 0x42;
                break;
            case 0x0C:
                chAsciiL = 0x43;
                break;
            case 0x0D:
                chAsciiL = 0x44;
                break;
            case 0X0E:
                chAsciiL = 0x45;
                break;
            case 0x0F:
                chAsciiL = 0x46;
                break;
            default:
                return;
        }

        chAsciiHOut.setValue(chAsciiH);
        chAsciiLOut.setValue(chAsciiL);
        return;
    }

    public static boolean AsciiToHex(byte chAsciiH, byte chAsciiL, ByteValue byAt) {
        byte byAtH = 0x00;
        byte byAtL = 0x00;

        if ((chAsciiH >= 0x30) && (chAsciiH <= 0x39)) {
            byAtH = (byte) (chAsciiH - 0x30);
        } else if ((chAsciiH >= 0x41) && (chAsciiH <= 0x46)) {
            byAtH = (byte) (chAsciiH - 0x37);
        } else {
            return false;
        }

        if ((chAsciiL >= 0x30) && (chAsciiL <= 0x39)) {
            byAtL = (byte) (chAsciiL - 0x30);
        } else if ((chAsciiL >= 0x41) && (chAsciiL <= 0x46)) {
            byAtL = (byte) (chAsciiL - 0x37);
        } else {
            return false;
        }

        byAt.setValue((byte) ((byAtH << 4) + byAtL));
        return true;
    }

    boolean GetBccCode(byte[] arrCmdBcc, byte byValue) {
        int iSize = arrCmdBcc.length - 1;
        if (iSize < 11) {
            return false;
        }

        byte byAt = arrCmdBcc[1];
        byValue = byAt;
        for (int i = 1; i < (iSize - 1); i++) {
            byValue = (byte) (byValue ^ arrCmdBcc[i + 1]);
        }

        return true;
    }

    public static boolean PackCmdComli2(byte byId, int dwAddr, byte byNoB, byte[] arrData, ByteArrayValue arrCmd) {
        // 命令参数长度检查
        int iDataSize = arrData.length;
        if (iDataSize > 13) {
            return false;
        }

        // 初始化命令长度
        byte[] byArrCmd = new byte[13];
        arrCmd.setValue(byArrCmd);

        ByteValue chASCIIH = new ByteValue();
        ByteValue chASCIIL = new ByteValue();
        int pos = 0;

        // 包头
        byArrCmd[pos++] = 0;
        byArrCmd[pos++] = 0x02;

        // 目标设备类型
        HexToAscii(byId, chASCIIH, chASCIIL);
        byArrCmd[pos++] = chASCIIH.getValue();
        byArrCmd[pos++] = chASCIIL.getValue();

        // Stamp
        byArrCmd[pos++] = 0x31;

        // 消息类型
        byArrCmd[pos++] = 0x32;

        // 这四个字节给出要读取的数据区域的起始地址
        HexToAscii((byte) (dwAddr / 0x100), chASCIIH, chASCIIL);
        byArrCmd[pos++] = chASCIIH.getValue();
        byArrCmd[pos++] = chASCIIL.getValue();

        HexToAscii((byte) (dwAddr % 0x100), chASCIIH, chASCIIL);
        byArrCmd[pos++] = chASCIIH.getValue();
        byArrCmd[pos++] = chASCIIL.getValue();

        // 要读取的数据的字节数
        HexToAscii(byNoB, chASCIIH, chASCIIL);
        byArrCmd[pos++] = chASCIIH.getValue();
        byArrCmd[pos++] = chASCIIL.getValue();

        // 包尾
        byArrCmd[pos++] = 0x03;

        // 校验和
        ByteValue byBcc = new ByteValue();
        if (!GetBccCode(arrCmd.getValue(), byBcc)) {
            return false;
        }

        byArrCmd[pos++] = byBcc.getValue();

        return true;

    }

    public static boolean UnPackCmdComli0(byte[] arrCmd, ByteValue wMId, ByteValue byMsgty, IntegerValue dwAddr,
        ByteArrayValue arrData) {

        int iSize = arrCmd.length;
        if (iSize < 13) {
            return false;
        }

        // 返回命令的协议结构
        // SOI MID Stamp Message Type Addr No Of Bytes INFO EOI CHKSUM
        // 起始位 设备标识 信息标签 信息类型 起始地址 数据块字节数 数据 结束码 校验
        // 1 2 1 1 4 2 n 1 1

        // byte* byArrCmd = arrCmd.GetData()+1;
        // byte* byAt = NULL;
        byte chASCIIH, chASCIIL;
        ByteValue byAt = new ByteValue();
        int pos = 1;

        // 起始位SOI
        if (arrCmd[0] != 0x02) {
            return false;
        }

        // 结束码EOI
        if (arrCmd[iSize - 2] != 0x03) {
            return false;
        }

        // 设备标识MID
        chASCIIH = arrCmd[pos++];
        chASCIIL = arrCmd[pos++];
        if (!AsciiToHex(chASCIIH, chASCIIL, byAt)) {
            return false;
        }
        wMId.setValue(byAt.getValue());
        // 信息标签
        byte byStamp = arrCmd[pos++];

        // 信息类型
        byMsgty.setValue(arrCmd[pos++]);

        // 起始地址
        chASCIIH = arrCmd[pos++];
        chASCIIL = arrCmd[pos++];
        if (!AsciiToHex(chASCIIH, chASCIIL, byAt)) {
            return false;
        }
        byte byAddr1 = byAt.getValue();
        chASCIIH = arrCmd[pos++];
        chASCIIL = arrCmd[pos++];
        if (!AsciiToHex(chASCIIH, chASCIIL, byAt)) {
            return false;
        }
        byte byAddr2 = byAt.getValue();
        dwAddr.setValue(byAddr1 + byAddr2 << 8);

        int wNoB = 0;
        chASCIIH = arrCmd[pos++];
        chASCIIL = arrCmd[pos++];
        if (!AsciiToHex(chASCIIH, chASCIIL, byAt)) {
            return false;
        }
        wNoB = byAt.getValue();

        int iDataSize = wNoB;
        arrData.setValue(new byte[iDataSize]);

        // 数据INFO
        byte[] byDat = arrData.getValue();
        int dpos = 0;
        for (int i = 0; i < iDataSize; i++) {
            byDat[dpos++] = NegativeSequence(arrCmd[pos++]);
        }

        // 校验和
        ByteValue byBccTemp = new ByteValue();
        byte byBcc = arrCmd[iSize - 1];
        if (!GetBccCode(arrCmd, byBccTemp)) {
            return false;
        }
        if (byBcc != byBccTemp.getValue()) {
            return false;
        }

        return true;
    }

    public static boolean GetBccCode(byte[] arrCmdBcc, ByteValue byValue) {
        int iSize = arrCmdBcc.length - 1;
        if (iSize < 3) {
            return false;
        }

        int pos = 1;
        byte byBcc = arrCmdBcc[pos];
        for (int i = 1; i < (iSize - 1); i++) {
            byte at = arrCmdBcc[++pos];
            byBcc = (byte) (byBcc ^ at);
        }

        byValue.setValue(byBcc);
        return true;
    }

}
