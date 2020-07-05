
package com.huawei.roc.protocol.dlt719.def;

public class Dlt719Char {
    public final byte FStartChar = 0x10;     // 固定帧长帧格式的启动字符

    public final byte FEndChar = 0x16;     // 固定帧长帧格式的结束字符

    public final byte VEndChar = FEndChar; // 可变帧长帧格式的结束字符

    public final byte SEndChar = 0x5E;     // 单个字符的结束字符

    public final byte VStartChar = 0x68;     // 可变帧长帧格式的启动字符
}
