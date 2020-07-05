
package com.huawei.roc.protocol.dlt719.def;

//控制域的功能码(上行)
public class Dlt719CtrlFucU {
    public final byte Dlt719_CtrlFucU_Confirm = 0; // 帧类型:确认; 功能:确认.

    public final byte Dlt719_CtrlFucU_NotRecv = 1; // 帧类型:确认; 功能:链路忙,没有收到报文.

    public final byte Dlt719_CtrlFucU_Data = 8; // 帧类型:响应; 功能:以数据回答请求帧.

    public final byte Dlt719_CtrlFucU_NotData = 9; // 帧类型:响应; 功能:没有召唤的数据.

    public final byte Dlt719_CtrlFucU_LnStateOrAsk = 11;  // 帧类型:响应; 功能:以链路状态或访问请求回答请求帧.
}
