/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

/**
 * CRC16_CCITT：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在前，高位在后，结果与0x0000异或
 * CRC16_CCITT_FALSE：多项式x16+x12+x5+1（0x1021），初始值0xFFFF，低位在后，高位在前，结果与0x0000异或
 * CRC16_XMODEM：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在后，高位在前，结果与0x0000异或
 * CRC16_X25：多项式x16+x12+x5+1（0x1021），初始值0xffff，低位在前，高位在后，结果与0xFFFF异或
 * CRC16_MODBUS：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0x0000异或
 * CRC16_IBM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0x0000异或
 * CRC16_MAXIM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
 * CRC16_USB：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0xFFFF异或
 * CRC16_DNP：多项式x16+x13+x12+x11+x10+x8+x6+x5+x2+1（0x3D65），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
 * <p>
 * （1）、预置1个16位的寄存器为十六进制FFFF（即全为1），称此寄存器为CRC寄存器；
 * （2）、把第一个8位二进制数据（既通讯信息帧的第一个字节）与16位的CRC寄存器的低8位相异或，把结果放于CRC寄存器，高八位数据不变；
 * （3）、把CRC寄存器的内容右移一位（朝低位）用0填补最高位，并检查右移后的移出位；
 * （4）、如果移出位为0：重复第3步（再次右移一位）；如果移出位为1，CRC寄存器与多项式A001（1010 0000 0000 0001）进行异或；
 * （5）、重复步骤3和4，直到右移8次，这样整个8位数据全部进行了处理；
 * （6）、重复步骤2到步骤5，进行通讯信息帧下一个字节的处理；
 * （7）、将该通讯信息帧所有字节按上述步骤计算完成后，得到的16位CRC寄存器的高、低字节进行交换；
 * （8）、最后得到的CRC寄存器内容即为：CRC码。
 * <p>
 * 以上计算步骤中的多项式0xA001是0x8005按位颠倒后的结果。
 * 0x8408是0x1021按位颠倒后的结果。
 * 在线校验工具
 * http://www.ip33.com/crc.html
 * https://blog.csdn.net/htmlxx/article/details/17369105
 * <p>
 * @author Water
 * @since 2018/11/19 0019 15:03
 */
public class CRC16Utils {
    /**
     * CRC16计算（异或）
     * @param buffer 缓存数组
     * @param initial 初始值
     * @param polynomial 多项式
     * @param xor 异或值
     * @return CRC值
     */
    public static int getCRC16XOR(byte[] buffer, int initial, int polynomial, int xor) {
        int wCRCin = initial;
        int wCPoly = polynomial;
        for (byte byB : buffer) {
            wCRCin ^= ((int) byB & 0x00ff);
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
     * CRC16计算（与）
     * @param buffer 缓存数组
     * @param initial 初始值
     * @param polynomial 多项式
     * @param and 与值
     * @return CRC值
     */
    public static int getCRC16AND(byte[] buffer, int initial, int polynomial, int and) {
        int wCRCin = initial;
        int wCPoly = polynomial;
        for (byte byB : buffer) {
            for (int iPos = 0; iPos < 8; iPos++) {
                boolean bit = ((byB >> (7 - iPos) & 1) == 1);
                boolean c15 = ((wCRCin >> 15 & 1) == 1);
                wCRCin <<= 1;
                if (c15 ^ bit) {
                    wCRCin ^= wCPoly;
                }
            }
        }
        return wCRCin &= and;
    }

    /**
     * CRC16_CCITT：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在前，高位在后，结果与0x0000异或
     * 0x8408是0x1021按位颠倒后的结果。
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16CCITT(byte[] buffer) {
        return getCRC16XOR(buffer, 0x0000, 0x8408, 0x0000);
    }

    /**
     * CRC-CCITT (0xFFFF)
     * CRC16_CCITT_FALSE：多项式x16+x12+x5+1（0x1021），初始值0xFFFF，低位在后，高位在前，结果与0x0000异或
     *
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16CCITTFALSE(byte[] buffer) {
        return getCRC16AND(buffer, 0xffff, 0x1021, 0xffff);
    }

    /**
     * CRC-CCITT (XModem)
     * CRC16_XMODEM：多项式x16+x12+x5+1（0x1021），初始值0x0000，低位在后，高位在前，结果与0x0000异或
     *
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16XMODEM(byte[] buffer) {
        return getCRC16AND(buffer, 0x0000, 0x1021, 0xffff);
    }

    /**
     * CRC16_X25：多项式x16+x12+x5+1（0x1021），初始值0xffff，低位在前，高位在后，结果与0xFFFF异或
     * 0x8408是0x1021按位颠倒后的结果。
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16X25(byte[] buffer) {
        return getCRC16XOR(buffer, 0xffff, 0x8408, 0xffff);
    }

    /**
     * CRC-16 (Modbus)
     * CRC16_MODBUS：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0x0000异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16MODBUS(byte[] buffer) {
        return getCRC16XOR(buffer, 0xffff, 0xa001, 0x0000);
    }

    /**
     * CRC-16
     * CRC16_IBM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0x0000异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16IBM(byte[] buffer) {
        return getCRC16XOR(buffer, 0x0000, 0xa001, 0x0000);
    }

    /**
     * CRC16_MAXIM：多项式x16+x15+x2+1（0x8005），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16MAXIM(byte[] buffer) {
        return getCRC16XOR(buffer, 0x0000, 0xa001, 0xffff);
    }

    /**
     * CRC16_USB：多项式x16+x15+x2+1（0x8005），初始值0xFFFF，低位在前，高位在后，结果与0xFFFF异或
     * 0xA001是0x8005按位颠倒后的结果
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16USB(byte[] buffer) {
        return getCRC16XOR(buffer, 0xFFFF, 0xa001, 0xffff);
    }

    /**
     * CRC16_DNP：多项式x16+x13+x12+x11+x10+x8+x6+x5+x2+1（0x3D65），初始值0x0000，低位在前，高位在后，结果与0xFFFF异或
     * 0xA6BC是0x3D65按位颠倒后的结果
     * @param buffer 缓存数组
     * @return CRC值
     */
    public static int getCRC16DNP(byte[] buffer) {
        return getCRC16XOR(buffer, 0x0000, 0xA6BC, 0xffff);
    }
}