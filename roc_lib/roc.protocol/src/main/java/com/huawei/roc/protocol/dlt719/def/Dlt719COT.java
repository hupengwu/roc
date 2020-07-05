
package com.huawei.roc.protocol.dlt719.def;

//传送原因(Cause of transmission) 6bit
public class Dlt719COT {
    public final byte Dlt719_COT_NoUse = 0;   // 未用

    public final byte Dlt719_COT_Test = 1;   // 试验（专用范围定义）

    public final byte Dlt719_COT_Cycle = 2;   // 周期、循环（专用范围定义）

    public final byte Dlt719_COT_Out = 3;   // 自发（突发）

    public final byte Dlt719_COT_Init = 4;   // 初始化

    public final byte Dlt719_COT_Ask = 5;   // 请求或被请求

    public final byte Dlt719_COT_Act = 6;   // 激活

    public final byte Dlt719_COT_ActTrue = 7;   // 激活确认 activation confirm

    public final byte Dlt719_COT_ActOff = 8;   // 停止激活

    public final byte Dlt719_COT_deactcon = 9;   // 停止激活确认deactcon

    public final byte Dlt719_COT_ActEnd = 10;  // 激活终止

    public final byte Dlt719_COT_NoAskRec = 13;  // 无所请求的数据记录

    public final byte Dlt719_COT_NoAskASDU = 14;  // 无所请求的应用服务数据单元--类型

    public final byte Dlt719_COT_NoASDURecID = 15;  // 由主站(控制站)发送的应用服务数据单元中的记录序号不可知

    public final byte Dlt719_COT_NoASDUAddID = 16;  // 由主站(控制站)发送的应用服务数据单元中的地址说明不可知

    public final byte Dlt719_COT_NoAskInf = 17;  // 无所请求的信息体

    public final byte Dlt719_COT_NoAskTime = 18;  // 无所请求的时间段

    public final byte Dlt719_COT_SynTime = 48;  // 时间同步(专用范围定义)

    public final byte Dlt719_COT_Set = 49;  // 设置/被设置

    public final byte Dlt719_COT_NoPerform = 50;  // 密码权限不足

    public final byte Dlt719_COT_End = 63;
}
