
package com.huawei.roc.protocol.modbus;

import com.huawei.roc.protocol.modbus.def.Mode;
import com.huawei.roc.value.ByteArrayValue;
import com.huawei.roc.value.ByteValue;
import com.huawei.roc.value.ShortArrayValue;
import com.huawei.roc.value.ShortValue;

public class ModBusCmd {
    /**
     * 打包报文
     * 
     * @param byAddr 从站地址
     * @param byFun 功能码
     * @param arrData 数据
     * @param arrCmdValue 报文
     * @return 编码是否成功
     */
    public static boolean PackCmdRTU(byte byAddr, byte byFun, byte[] arrData, ByteArrayValue arrCmdValue) {
        int iSize = arrData.length;

        arrCmdValue.setValue(new byte[iSize + 4]);
        byte[] arrCmd = arrCmdValue.getValue();

        // Slave Address 11
        // Function 01
        // Starting Address Hi 00
        // Starting Address Lo 13
        // No. of Points Hi 00
        // No. of Points Lo 25
        // Error Check (LRC or CRC) ––

        // 地址码
        arrCmd[0] = byAddr;

        // 功能码
        arrCmd[1] = byFun;

        // 数据域
        System.arraycopy(arrData, 0, arrCmd, 2, iSize);

        // 校验CRC
        int wCrc16 = GetCRC16(arrCmd);
        arrCmd[arrCmd.length - 2] = (byte) (wCrc16 % 0x100);
        arrCmd[arrCmd.length - 1] = (byte) (wCrc16 / 0x100);

        return true;
    }

    /**
     * 解包报文
     * @param arrCmd 报文
     * @param byAddrValue 地址码
     * @param byFunValue 功能码
     * @param arrDataValue 数据段
     * @return 是否成功
     */
    public static boolean UnPackCmdRTU(byte[] arrCmd, ByteValue byAddrValue, ByteValue byFunValue,
        ByteArrayValue arrDataValue) {
        int iSize = arrCmd.length;
        if (iSize < 4) {
            return false;
        }

        // Slave Address 11
        // Function 01
        // Byte Count 05
        // Data (Coils 27–20) CD
        // Data (Coils 35–28) 6B
        // Data (Coils 43–36) B2
        // Data (Coils 51–44) 0E
        // Data (Coils 56–52) 1B
        // Error Check (LRC or CRC) ––

        // 地址码
        byte byAddr = arrCmd[0];
        byAddrValue.setValue(byAddr);

        // 功能码
        byte byFun = arrCmd[1];
        byFunValue.setValue(byFun);

        // 数据域
        int iDataSize = iSize - 4;
        arrDataValue.setValue(new byte[iDataSize]);
        byte[] arrData = arrDataValue.getValue();
        System.arraycopy(arrCmd, 2, arrData, 0, iDataSize);

        // 校验CRC
        int wCrc16OK = GetCRC16(arrCmd);
        byte crcH = (byte) (wCrc16OK & 0xff);
        byte crcL = (byte) ((wCrc16OK & 0xff00) >> 8);
        if (arrCmd[arrCmd.length - 1] == crcL && arrCmd[arrCmd.length - 2] == crcH) {
            return true;
        }

        return false;
    }

    /**
     * CRC16计算（异或）
     * @param buffer 缓存数组
     * @param initial 初始值
     * @param polynomial 多项式
     * @param xor 异或值
     * @return CRC值
     */
    public static int getCRC16XOR(byte[] buffer, int length, int initial, int polynomial, int xor) {
        int wCRCin = initial;
        int wCPoly = polynomial;
        for (int i = 0; i < length; i++) {
            wCRCin ^= ((int) buffer[i] & 0x00ff);
            for (int jPos = 0; jPos < 8; jPos++) {
                if ((wCRCin & 0x0001) != 0) {
                    wCRCin >>= 1;
                    wCRCin ^= wCPoly;
                } else {
                    wCRCin >>= 1;
                }
            }
        }
        return wCRCin ^= xor;
    }

    /**
     * 计算CRC16校验码
     * 
     * @param arrCmd 报文
     * @return CRC16校验码
     */
    public static int GetCRC16(byte[] arrCmd) {
        return getCRC16XOR(arrCmd, arrCmd.length - 2, 0xffff, 0xa001, 0x0000);
    }

    /**
     * 打包成ASCII格式
     * 
     * @param byAddr 地址码
     * @param byFun 功能码
     * @param arrData 数据段
     * @param arrCmdValue 报文
     * @return 是否打包成功
     */
    public static boolean PackCmdASCII(byte byAddr, byte byFun, byte[] arrData, ByteArrayValue arrCmdValue) {
        int iDataSize = arrData.length;

        arrCmdValue.setValue(new byte[2 * iDataSize + 9]);
        byte[] arrCmd = arrCmdValue.getValue();

        // Slave Address 11
        // Function 01
        // Starting Address Hi 00
        // Starting Address Lo 13
        // No. of Points Hi 00
        // No. of Points Lo 25
        // Error Check (LRC or CRC) ––

        ByteValue chHighAsciiValue = new ByteValue();
        ByteValue chLowAsciiValue = new ByteValue();
        // byte chHighAscii, chLowAscii;
        byte byAt = 0;

        // 头标
        arrCmd[byAt++] = 0x3A;

        // 地址码
        HexToAscii(byAddr, chHighAsciiValue, chLowAsciiValue);
        arrCmd[byAt++] = chHighAsciiValue.getValue();
        arrCmd[byAt++] = chLowAsciiValue.getValue();

        // 功能码
        HexToAscii(byFun, chHighAsciiValue, chLowAsciiValue);
        arrCmd[byAt++] = chHighAsciiValue.getValue();
        arrCmd[byAt++] = chLowAsciiValue.getValue();

        // 数据域
        // byte *byDat = arrData.GetData();
        for (int i = 0; i < iDataSize; i++) {
            HexToAscii(arrData[i], chHighAsciiValue, chLowAsciiValue);
            arrCmd[byAt++] = chHighAsciiValue.getValue();
            arrCmd[byAt++] = chLowAsciiValue.getValue();
        }

        // 校验CRC
        ShortValue wLRC8 = new ShortValue();
        if (!GetLRC8(arrCmd, wLRC8)) {
            return false;
        }
        arrCmd[byAt++] = (byte) (wLRC8.getValue() / 0x100);
        arrCmd[byAt++] = (byte) (wLRC8.getValue() % 0x100);

        // 结束符
        arrCmd[byAt++] = 0x0D;
        arrCmd[byAt++] = 0x0A;

        return true;
    }

    public static boolean UnPackCmdASCII(byte[] arrCmd, ByteValue byAddrValue, ByteValue byFunValue,
        ByteArrayValue arrDataValue) {
        // Slave Address 11
        // Function 01
        // Byte Count 05
        // Data (Coils 27–20) CD
        // Data (Coils 35–28) 6B
        // Data (Coils 43–36) B2
        // Data (Coils 51–44) 0E
        // Data (Coils 56–52) 1B
        // Error Check (LRC or CRC) ––

        int iSize = arrCmd.length;
        if (iSize < 9) {
            return false;
        }
        if ((iSize % 2) != 1) {
            return false;
        }

        // byte* byArrCmd = arrCmd.GetData()+1;
        byte byArrCmd = 1;
        // byte* byAt = NULL;

        byte chHigh, chLow;

        // 起始位
        if (arrCmd[0] != 0x3A) {
            return false;
        }

        // 结束码
        if (arrCmd[iSize - 2] != 0x0D) {
            return false;
        }
        if (arrCmd[iSize - 1] != 0x0A) {
            return false;
        }

        // 设备地址ADR
        chHigh = arrCmd[byArrCmd++];
        chLow = arrCmd[byArrCmd++];
        if (!AsciiToHex(chHigh, chLow, byAddrValue)) {
            return false;
        }

        // 功能码
        chHigh = arrCmd[byArrCmd++];
        chLow = arrCmd[byArrCmd++];
        if (!AsciiToHex(chHigh, chLow, byFunValue)) {
            return false;
        }

        // 初始化数据域
        int iDataSize = (iSize - 9) / 2;
        arrDataValue.setValue(new byte[iDataSize]);
        byte[] arrData = arrDataValue.getValue();
        byte byAt = 0;
        ByteValue byAtValue = new ByteValue();
        for (int i = 0; i < iDataSize; i++) {
            chHigh = arrCmd[byArrCmd++];
            chLow = arrCmd[byArrCmd++];
            if (!AsciiToHex(chHigh, chLow, byAtValue)) {
                return false;
            }
            arrData[byAt++] = byAtValue.getValue();
        }

        // 校验
        short wVfy = (short) (arrCmd[byArrCmd++] * 0x100);
        wVfy += arrCmd[byArrCmd++];

        // 检查:校验和
        ShortValue wVfyOKValue = new ShortValue();
        if (!GetLRC8(arrCmd, wVfyOKValue)) {
            return false;
        }
        if (wVfyOKValue.getValue() != wVfy) {
            return false;
        }

        return true;
    }

    /**
     * 16进制转换为ASCII格式
     * 
     * @param byAt 16进制
     * @param chAsciiHValue 高位ASCII
     * @param chAsciiLValue 低位ASCII
     */
    private static void HexToAscii(byte byAt, ByteValue chAsciiHValue, ByteValue chAsciiLValue) {
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

        chAsciiHValue.setValue(chAsciiH);
        chAsciiLValue.setValue(chAsciiL);
        return;
    }

    /**
     * 生成CRC8编码
     * 
     * @param arrCmd 报文
     * @param wLRC8Value CRC8编码
     * @return
     */
    private static boolean GetLRC8(byte[] arrCmd, ShortValue wLRC8Value) {
        int iSize = arrCmd.length - 5;
        if (iSize < 4) {
            return false;
        }
        if (iSize % 2 != 0) {
            return false;
        }

        // byte *byAt = arrCmd.GetData()+1;
        byte byAt = 1;

        byte chHigh, chLow;
        ByteValue byHexAt = new ByteValue();

        byte uchLRC = 0; // LRC char initialized

        do  // pass through message buffer
        {
            chHigh = arrCmd[byAt++];
            chLow = arrCmd[byAt++];
            if (!AsciiToHex(chHigh, chLow, byHexAt)) {
                return false;
            }

            uchLRC += byHexAt.getValue(); // add buffer byte without carry

            iSize -= 2;
        } while (iSize > 0);

        byte byLRC = (byte) (-((char) uchLRC));

        ByteValue chHighValue = new ByteValue();
        ByteValue chLowValue = new ByteValue();
        HexToAscii(byLRC, chHighValue, chLowValue);

        wLRC8Value.setValue((short) ((((short) chHighValue.getValue()) << 8) | chLowValue.getValue()));

        return true;
    }

    /**
     * ASCII转16进制
     * 
     * @param chAsciiH 高位ASCII
     * @param chAsciiL 低位ASCII
     * @param byAt 16进制编码
     * @return 是否成功
     */
    private static boolean AsciiToHex(byte chAsciiH, byte chAsciiL, ByteValue byAt) {
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

    public static boolean PackCmd(byte byAddr, byte byFun, byte[] arrData, ByteArrayValue arrCmd, Mode mode) {
        if (Mode.ASCII.equals(mode)) {
            return PackCmdASCII(byAddr, byFun, arrData, arrCmd);
        }
        if (Mode.RTU.equals(mode)) {
            return PackCmdRTU(byAddr, byFun, arrData, arrCmd);
        }

        return false;
    }

    public static boolean UnPackCmd(byte[] arrCmd, ByteValue byAddr, ByteValue byFun, ByteArrayValue arrData,
        Mode mode) {
        if (Mode.ASCII.equals(mode)) {
            return UnPackCmdASCII(arrCmd, byAddr, byFun, arrData);
        }
        if (Mode.RTU.equals(mode)) {
            return UnPackCmdRTU(arrCmd, byAddr, byFun, arrData);
        }

        return false;
    }

    /**
     * 读取线圈状态的编码函数
     * 
     * @param byAddr 设备地址
     * @param wAddress 地址偏移量
     * @param wCount 线圈数量
     * @param mode 工作模式
     * @param arrCmdValue 命令编码 
     * @return 是否成功
     */
    public static boolean PackCmdReadCoilStatus(byte byAddr, short wAddress, short wCount, ByteArrayValue arrCmdValue,
        Mode mode) {
        byte[] arrData = new byte[4];

        // Slave Address 11
        // Function 01
        // Starting Address Hi 00
        // Starting Address Lo 13
        // No. of Points Hi 00
        // No. of Points Lo 25
        // Error Check (LRC or CRC) ––

        arrData[0] = (byte) (wAddress / 0x100);
        arrData[1] = (byte) (wAddress % 0x100);
        arrData[2] = (byte) (wCount / 0x100);
        arrData[3] = (byte) (wCount % 0x100);

        return PackCmd(byAddr, (byte) 0x01, arrData, arrCmdValue, mode);
    }

    public static boolean UnPackCmdReadCoilStatus(byte[] arrCmd, ByteValue byAddr, ShortValue wCountValue,
        ByteArrayValue arrStatusValue, Mode mode) {
        ByteValue byFun = new ByteValue();
        ByteArrayValue arrDataValue = new ByteArrayValue();
        if (!UnPackCmd(arrCmd, byAddr, byFun, arrDataValue, mode)) {
            return false;
        }

        // Slave Address 11
        // Function 01

        // Byte Count 05
        // Data (Coils 27–20) CD
        // Data (Coils 35–28) 6B
        // Data (Coils 43–36) B2
        // Data (Coils 51–44) 0E
        // Data (Coils 56–52) 1B

        // Error Check (LRC or CRC) ––

        if (byFun.getValue() != 0x01) {
            return false;
        }

        byte[] arrData = arrDataValue.getValue();
        int iDataSize = arrData.length;
        if (iDataSize < 1) {
            return false;
        }
        if (arrData[0] != iDataSize - 1) {
            return false;
        }
        wCountValue.setValue(arrData[0]);
        short wCount = wCountValue.getValue();

        if ((wCount + 1) != arrData.length) {
            return false;
        }

        // 初始化数组大小
        arrStatusValue.setValue(new byte[wCount]);
        byte[] arrStatus = arrStatusValue.getValue();

        byte byStatus = 0;
        iDataSize = iDataSize - 2;
        for (int i = 0; i < iDataSize; i++) {
            byte byAt = arrData[i];
            byte byBit = 0x01;

            for (int k = 0; k < 8; k++) {
                if ((byte) (byAt & byBit) != 0) {
                    arrStatus[byStatus++] = (byte) 0xFF;
                } else {
                    arrStatus[byStatus++] = (byte) 0x00;
                }

                byBit <<= 1;
            }
        }

        int iStatusSize = arrStatus.length % 8;
        if (iStatusSize > 0) {
            byte byAt = arrData[arrData.length - 1];
            byte byBit = 0x01;

            for (int k = 0; k < iStatusSize; k++) {
                if ((byte) (byAt & byBit) != 0) {
                    arrStatus[byStatus++] = (byte) 0xFF;
                } else {
                    arrStatus[byStatus++] = (byte) 0x00;
                }

                byBit <<= 1;
            }
        }

        return true;
    }

    boolean PackCmdReadInputStatus(byte byAddr, short wAddress, short wCount, ByteArrayValue arrCmdValue, Mode mode) {
        byte[] arrData = new byte[4];

        // Slave Address 11
        // Function 02
        // Starting Address Hi 00
        // Starting Address Lo C4
        // No. of Points Hi 00
        // No. of Points Lo 16
        // Error Check (LRC or CRC) ––

        arrData[0] = (byte) (wAddress / 0x100);
        arrData[1] = (byte) (wAddress % 0x100);
        arrData[2] = (byte) (wCount / 0x100);
        arrData[3] = (byte) (wCount % 0x100);

        return PackCmd(byAddr, (byte) 0x02, arrData, arrCmdValue, mode);
    }

    // ======================================================================
    // 函数名称:UnPackCmdReadInputStatus
    // 功能描述:读取开关输入状态
    // 输入参数:arrCmd=数据编码
    // wCount=线圈数量
    // 输出参数:arrStatus=线圈状态
    // byAddr=设备地址
    // 返 回 值:操作是否成功
    // 创建日期:2006-03-27
    // 修改日期:
    // 作 者:胡鹏武
    // 备 注:MODBUS设备是仿照内存管理方式来进行数据的管理的
    // 取得一组开关输入的当前状态（ON/OFF) ,并将开关输入状态的保存于
    // 数组当中开关输入状态数组元素的0xFF/0x00分别对应ON/OFF状态
    // ======================================================================
    boolean UnPackCmdReadInputStatus(byte[] arrCmd, ByteValue byAddr, ShortValue wCountValue,
        ByteArrayValue arrStatusValue, Mode mode) {
        ByteValue byFunValue = new ByteValue();
        ByteArrayValue arrDataValue = new ByteArrayValue();
        if (!UnPackCmd(arrCmd, byAddr, byFunValue, arrDataValue, mode)) {
            return false;
        }

        // Slave Address 11
        // Function 02

        // Byte Count 03
        // Data (Inputs 10204–10197) AC
        // Data (Inputs 10212–10205) DB
        // Data (Inputs 10218–10213) 35

        // Error Check (LRC or CRC) –– ––

        if (byFunValue.getValue() != 0x02) {
            return false;
        }

        byte[] arrData = arrDataValue.getValue();
        int iDataSize = arrData.length;
        if (iDataSize < 1) {
            return false;
        }
        if (arrData[0] != iDataSize - 1) {
            return false;
        }

        wCountValue.setValue((short) (arrData[0] * 8));

        // 初始化数组大小
        arrStatusValue.setValue(new byte[wCountValue.getValue()]);
        byte[] arrStatus = arrStatusValue.getValue();

        // byte *byStatus = arrStatus.GetData();
        int byStatus = 0;
        iDataSize = iDataSize - 2;
        for (int i = 0; i < iDataSize; i++) {
            byte byAt = arrData[i];
            byte byBit = 0x01;

            for (int k = 0; k < 8; k++) {
                if ((byAt & byBit) != 0) {
                    arrStatus[byStatus++] = (byte) 0xFF;
                } else {
                    arrStatus[byStatus++] = 0x00;
                }

                byBit <<= 1;
            }
        }

        int iStatusSize = arrStatus.length % 8;
        if (iStatusSize > 0) {
            byte byAt = arrData[arrData.length - 1];
            byte byBit = 0x01;

            for (int k = 0; k < iStatusSize; k++) {
                if ((byAt & byBit) != 0) {
                    arrStatus[byStatus++] = (byte) 0xFF;
                } else {
                    arrStatus[byStatus++] = 0x00;
                }

                byBit <<= 1;
            }
        }

        return true;
    }

    public static boolean PackCmdReadHoldingRegisters(byte byAddr, short wAddress, short wCount, ByteArrayValue arrCmd,
        Mode mode) {
        ByteArrayValue arrDataVaule = new ByteArrayValue();
        arrDataVaule.setValue(new byte[4]);
        byte[] arrData = arrDataVaule.getValue();

        // Slave Address 11
        // Function 03
        // Starting Address Hi 00
        // Starting Address Lo 6B
        // No. of Points Hi 00
        // No. of Points Lo 03
        // Error Check (LRC or CRC) ––

        arrData[0] = (byte) (wAddress / 0x100);
        arrData[1] = (byte) (wAddress % 0x100);
        arrData[2] = (byte) (wCount / 0x100);
        arrData[3] = (byte) (wCount % 0x100);

        return PackCmd(byAddr, (byte) 0x03, arrData, arrCmd, mode);
    }

    public static boolean UnPackCmdReadHoldingRegisters(byte[] arrCmd, ByteValue byAddr, ShortArrayValue arrStatusValue,
        Mode mode) {
        ByteValue byFun = new ByteValue();
        ByteArrayValue arrDataValue = new ByteArrayValue();
        if (!UnPackCmd(arrCmd, byAddr, byFun, arrDataValue, mode)) {
            return false;
        }

        // Slave Address 11
        // Function 03
        // Byte Count 06
        // Data Hi (Register 40108) 02
        // Data Lo (Register 40108) 2B
        // Data Hi (Register 40109) 00
        // Data Lo (Register 40109) 00
        // Data Hi (Register 40110) 00
        // Data Lo (Register 40110) 64
        // Error Check (LRC or CRC) ––

        if (byFun.getValue() != 0x03) {
            return false;
        }

        byte[] arrData = arrDataValue.getValue();
        int iDataSize = arrData.length;
        if (iDataSize < 1) {
            return false;
        }
        if (arrData[0] != iDataSize - 1) {
            return false;
        }
        if (iDataSize % 2 != 1) {
            return false;
        }

        // 初始化数组大小
        short wCount = (short) ((iDataSize - 1) / 2);
        arrStatusValue.setValue(new short[wCount]);
        short[] arrStatus = arrStatusValue.getValue();

        // byte *byAt = arrData.GetData()+1;
        int byAt = 1;
        // short *byStatus = arrStatus.GetData();
        int byStatus = 0;
        for (int i = 0; i < wCount; i++) {
            arrStatus[byStatus++] = (short) ((((short) arrData[byAt]) << 8) + ((short) arrData[byAt + 1]));
            byAt += 2;
        }

        return true;
    }
}
