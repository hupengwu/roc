
package com.huawei.roc.frame.constant;

/**
 * 转义字符
 * 
 * @author h00442047
 * @since 2019年12月19日
 */
public class Escape {
    // 控制符
    public static final byte esc = (byte) 0xff;

    // 起始字符
    public static final byte begin = (byte) 0xfa;

    // 结束字符
    public static final byte end = (byte) 0xfb;

    // 冲突字符：控制字符
    public static final byte escEsc = (byte) 0x00;

    // 冲突字符：起始字符
    public static final byte escBegin = (byte) 0x01;

    // 冲突字符：结束字符
    public static final byte escEnd = (byte) 0x02;

}
