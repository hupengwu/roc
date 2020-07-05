/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.utils;

import java.util.List;

/**
 * 转义处理
 * @author h00442047
 * @since 2019/9/20
 */
public class EscapeUtils {
    /**
     * 查找转义字符的位置
     * @param input 转义后的数组
     * @param esc 转义字符
     * @return 位置，如果找不到的时候为-1
     */
    public static int findEscPos(byte[] input, byte esc) {
        for (int i = 0; i < input.length; i++) {
            byte bi = input[i];

            if (bi == esc) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 解转义数组列表：用来解决对端传输一个长串数据，因为网络原因，分裂成很多段的场景
     * @param inputs 转义数组 类似这样的字节数组fa122356ff00ff01ff02fb，这个数组被分成很多段，包含fa/fb这样的控制字符
     * @param esc 控制字符：冲突标识字符，是用于防止原始数组中含有控制字符，造成的冲突，比如FF
     * @param begin 控制字符：起始字符，用来表示开始，比如FA
     * @param end 控制字符：结束字符，用来表示结束，比如FB
     * @param escEsc 冲突字符：控制字符，原字符存在esc时用esc+该字符替换，比如00，是碰到FF时用FF00替换
     * @param escBegin 冲突字符：起始字符，原字符存在begin时用esc+该字符替换，比如01，是碰到FA时用FF01替换
     * @param escEnd 冲突字符：结束字符，原字符存在end时用esc+该字符替换，比如02，是碰到FB时用FF02替换
     * @return 原始数组，比如122356fffafb<br>
         * 如果出现异常，则返回长度为0的数组
     */
    public static byte[] unescapeByteArray(List<byte[]> inputs, byte esc, byte begin, byte end, byte escEsc,
        byte escBegin, byte escEnd) {
        // 检查是否为空
        if (inputs.isEmpty()) {
            return new byte[0];
        }

        // 寻找控制字符，起始/结束字符的位置
        int iBegin = -1;
        int kBegin = -1;
        int iEnd = -1;
        int kEnd = -1;
        for (int ipos = 0; ipos < inputs.size(); ipos++) {
            byte[] input = inputs.get(ipos);

            // 检查：是否找到了结尾
            if (iEnd != -1) {
                break;
            }

            for (int kpos = 0; kpos < input.length; kpos++) {
                byte kbyte = input[kpos];

                // 找到起始位置
                if (kbyte == begin) {
                    iBegin = ipos;
                    kBegin = kpos;

                    continue;
                }
                // 找到结束位置
                if (kbyte == end) {
                    iEnd = ipos;
                    kEnd = kpos;
                    break;
                }
            }
        }

        // 检查是否同时含有起始和结束控制字符
        if (iBegin == -1 || kBegin == -1 || iEnd == -1 || kEnd == -1) {
            return new byte[0];
        }

        // 计算数据的长度
        int length = 0;
        for (int ipos = iBegin; ipos < iEnd + 1; ipos++) {
            length += inputs.get(ipos).length;
        }
        byte[] outs = new byte[length];

        // 将碎片数据复制到连续性的数据中
        int dstPos = 0;
        for (int ipos = iBegin; ipos < iEnd + 1; ipos++) {
            byte[] input = inputs.get(ipos);
            System.arraycopy(input, 0, outs, dstPos, input.length);
            dstPos += input.length;
        }

        // 解码
        outs = EscapeUtils.unescapeByteArray(outs, esc, begin, end, escEsc, escBegin, escEnd);
        return outs;
    }

    /**
     * 转义数组
     * @param input 原数组 类似这样的字节数组122356fffafb
     * @param esc 控制字符：冲突标识字符，是用于防止原始数组中含有控制字符，造成的冲突，比如FF
     * @param begin 控制字符：起始字符，用来表示开始，比如FA
     * @param end 控制字符：结束字符，用来表示结束，比如FB
     * @param escEsc 冲突字符：控制字符，原字符存在esc时用esc+该字符替换，比如00，是碰到FF时用FF00替换
     * @param escBegin 冲突字符：起始字符，原字符存在begin时用esc+该字符替换，比如01，是碰到FA时用FF01替换
     * @param escEnd 冲突字符：结束字符，原字符存在end时用esc+该字符替换，比如02，是碰到FB时用FF02替换
     * @return 将原始数组转义成数组，比如fa122356ff00ff01ff02fb
     */
    public static byte[] escapeByteArray(byte[] input, byte esc, byte begin, byte end, byte escEsc, byte escBegin,
        byte escEnd) {
        // 目标数组的长度:原数组长度+首尾转义字符数+冲突字符数量
        int length = input.length + 2;
        for (int i = 0; i < input.length; i++) {
            // 原文中，跟控制字符冲突的数量
            byte bi = input[i];
            if (bi == begin || bi == end || bi == esc) {
                length++;
            }
        }

        // 转义处理
        byte[] outs = new byte[length];
        int kPos = 0;
        outs[kPos++] = begin;
        for (int i = 0; i < input.length; i++) {
            byte bi = input[i];

            if (bi == begin) {
                outs[kPos++] = esc;
                outs[kPos++] = escBegin;
            } else if (bi == end) {
                outs[kPos++] = esc;
                outs[kPos++] = escEnd;
            } else if (bi == esc) {
                outs[kPos++] = esc;
                outs[kPos++] = escEsc;
            } else {
                outs[kPos++] = bi;
            }
        }
        outs[kPos++] = end;

        return outs;
    }

    /**
     * 解转义数组
     * @param input 转义数组 类似这样的字节数组fa122356ff00ff01ff02fb
     * @param esc 控制字符：冲突标识字符，是用于防止原始数组中含有控制字符，造成的冲突，比如FF
     * @param begin 控制字符：起始字符，用来表示开始，比如FA
     * @param end 控制字符：结束字符，用来表示结束，比如FB
     * @param escEsc 冲突字符：控制字符，原字符存在esc时用esc+该字符替换，比如00，是碰到FF时用FF00替换
     * @param escBegin 冲突字符：起始字符，原字符存在begin时用esc+该字符替换，比如01，是碰到FA时用FF01替换
     * @param escEnd 冲突字符：结束字符，原字符存在end时用esc+该字符替换，比如02，是碰到FB时用FF02替换
     * @return 原始数组，比如122356fffafb<br>
         * 如果出现异常，则返回长度为0的数组
     */
    public static byte[] unescapeByteArray(byte[] input, byte esc, byte begin, byte end, byte escEsc, byte escBegin,
        byte escEnd) {
        // 找到起始控制字符
        int posBegin = -1;
        for (int ipos = 0; ipos < input.length; ipos++) {
            if (input[ipos] == begin) {
                posBegin = ipos;
                break;
            }
        }
        if (posBegin == -1) {
            return new byte[0];
        }

        // 找到结束控制字符
        int posEnd = -1;
        for (int ipos = posBegin + 1; ipos < input.length; ipos++) {
            if (input[ipos] == end) {
                posEnd = ipos;
                break;
            }
        }
        if (posEnd == -1) {
            return new byte[0];
        }

        int length = posEnd - posBegin + 1;

        // 确认转义后的长度
        int lengthOut = length - 2;
        for (int i = posBegin; i < length; i++) {
            if (input[i] == esc) {
                lengthOut--;
            }
        }

        byte[] outs = new byte[lengthOut];

        // 转义处理：扣掉头尾各1个长度
        int kpos = 0;
        for (int ipos = posBegin + 1; ipos < length - 1; ipos++) {
            byte bi = input[ipos];

            if (bi == esc) {
                ipos++;
                bi = input[ipos];
                if (bi == escBegin) {
                    outs[kpos++] = begin;
                } else if (bi == escEnd) {
                    outs[kpos++] = end;
                } else if (bi == escEsc) {
                    outs[kpos++] = esc;
                } else {
                    return new byte[0];
                }

            } else {
                outs[kpos++] = bi;
            }
        }

        return outs;
    }

    /**
     * 简单的转义处理
     * @param input 输入的字符串，比如12334567890
     * @param original 原始字符串，比如3
     * @param escape 转义字符串，比如33
     * @return 转义后的字符串，比如12334567890，转义后得到1233334567890
     */
    public static String escapeOneChar(String input, String original, String escape) {
        return input.replace(original, escape);
    }

    /**
     * 简单的还原处理：比如将转义后的1233334567890，33还原成3，得到12334567890
     * @param input 输入的字符串
     * @param original 原始字符串
     * @param escape 转义字符串
     * @return 转义后的字符串
     */
    public static String unescapeOneChar(String input, String original, String escape) {
        return input.replace(escape, original);
    }
}
