
package com.huawei.roc.protocol.dlt719.def;

//控制域的功能码(下行)
public class Dlt719CtrlFucD {
    public final byte Dlt719_CtrlFucD_Reset = 0; // 帧类型:发送/确认; 功能:复位通行单元.[FCV:0]

    public final byte Dlt719_CtrlFucD_Trans = 3; // 帧类型:发送/确认; 功能:传送数据.[FCV:1]

    public final byte Dlt719_CtrlFucD_LnState = 9; // 帧类型:请求/响应; 功能:召唤链路状态.[FCV:0]

    public final byte Dlt719_CtrlFucD_Data1 = 10; // 帧类型:请求/响应; 功能:召唤1级用户数据.[FCV:1]

    public final byte Dlt719_CtrlFucD_Data2 = 11;  // 帧类型:请求/响应; 功能:召唤2级用户数据.[FCV:1]
}
