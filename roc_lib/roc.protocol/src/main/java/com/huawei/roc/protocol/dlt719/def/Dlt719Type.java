
package com.huawei.roc.protocol.dlt719.def;

//类型标识符的语义 1 byte
public class Dlt719Type {

    // 1. 在监视方向上
    // <1> 类型标识的语义——在监视方向上的过程信息
    public final byte Dlt719_Type_NoUse = 0;   // 未用

    public final byte Dlt719_Type_M_SP_TA_2 = 1;   // 带时标的单点信息

    public final byte Dlt719_Type_M_IT_TA_2 = 2;   // 累计电量，每个量为四个字节

    public final byte Dlt719_Type_M_IT_TD_2 = 5;   // 周期电量增量，每个量为四个字节

    public final byte Dlt719_Type_M_IT_TG_2 = 8;   // 运行电能累计量，每个量为四个字节

    // 兼容定义
    public final byte Dlt719_Type_M_IT_TN_2 = 16;  // 费率电量，每个量为四个字节

    public final byte Dlt719_Type_M_IT_TO_2 = 17;  // 最大需量

    public final byte Dlt719_Type_M_IT_TP_2 = 18;  // 功率

    public final byte Dlt719_Type_M_IT_TQ_2 = 19;  // 功率因数

    public final byte Dlt719_Type_M_IT_TR_2 = 20;  // 电流

    public final byte Dlt719_Type_M_IT_TS_2 = 21;  // 电压

    public final byte Dlt719_Type_M_IT_TT_2 = 22;  // 失压记录

    // <2> 类型标识的语义——在监视方向上的系统信息
    public final byte Dlt719_Type_M_EI_NA_2 = 70;  // 初始化结束

    public final byte Dlt719_Type_P_MP_NA_2 = 71;  // 电能量数据终端设备的制造厂和产品规范

    public final byte Dlt719_Type_M_TI_TA_2 = 72;  // 电能量数据终端设备的当前系统时间

    // 兼容定义
    public final byte Dlt719_Type_M_SC_TA_2 = 97;  // 终端抄表方案

    public final byte Dlt719_Type_M_TB_TA_2 = 98;  // 数据信息体地址定义表

    public final byte Dlt719_Type_M_PT_TA_2 = 99;  // 计量点参数

    // 2. 控制方向上
    // <3> 类型标识的语义——在控制方向上的系统信息
    public final byte Dlt719_Type_C_RD_NA_2 = 100; // 读制造厂和产品规范

    public final byte Dlt719_Type_C_SP_NA_2 = 101; // 读带时标的单点信息的记录

    public final byte Dlt719_Type_C_SP_NB_2 = 102; // 读1个所选定时间范围的带时标的单点信息记录

    public final byte Dlt719_Type_C_TI_NA_2 = 103; // 读电能量数据终端设备的当前系统时间

    public final byte Dlt719_Type_C_CI_NA_2 = 104; // 读最早累计时段的记账(计费)电能累计量

    public final byte Dlt719_Type_C_CI_ND_2 = 107; // 读一个指定的过去累计时段和一个选定的地址范围的记账(计费)电能累计量

    public final byte Dlt719_Type_C_CI_NE_2 = 108; // 读周期地复位的最早累计时段的记账(计费)电能累计量

    public final byte Dlt719_Type_C_CI_NH_2 = 111; // 读一个指定的过去累计时段和一个选定的地址范围的周期地复位的记账(计费)电能累计量

    public final byte Dlt719_Type_C_CI_NR_2 = 120; // 读1个选定的时间范围和1个选定的地址范围的累计电量

    public final byte Dlt719_Type_C_CI_NS_2 = 121; // 读1个选定的时间范围和1个选定的地址范围的周期电量增量

    public final byte Dlt719_Type_C_CI_NT_2 = 122; // 读1个选定的时间范围和1个选定的地址范围的运行电能量

    public final byte Dlt719_Type_C_SYN_TA_2 = (byte) 128; // 同步终端时间帧

    // 3. 专用范围
    public final byte Dlt719_Type_S_CC_NT_2 = (byte) 140; // 密码校验

    public final byte Dlt719_Type_S_SC_NT_2 = (byte) 141; // 设置密码

    public final byte Dlt719_Type_S_RD_NM_2 = (byte) 142; // 读计量点参数

    public final byte Dlt719_Type_S_RD_NN_2 = (byte) 143; // 读信息体地址定义表

    public final byte Dlt719_Type_S_RD_NO_2 = (byte) 144; // 读终端抄表方案

    public final byte Dlt719_Type_S_SI_NO_2 = (byte) 145; // 设置计量点参数

    public final byte Dlt719_Type_S_SA_NO_2 = (byte) 146; // 设置或添加信息体地址定义表

    public final byte Dlt719_Type_S_ST_NO_2 = (byte) 147; // 设置终端抄表方案

    public final byte Dlt719_Type_S_DI_NO_2 = (byte) 148; // 删除计量点参数

    public final byte Dlt719_Type_S_DT_NO_2 = (byte) 149; // 清空信息体地址定义表

    // -- 科立 ---
    public final byte Dlt719_Type_C_REG_NA_2 = (byte) 150; // 读通道数据

    public final byte Dlt719_Type_C_EVENT_TA_2 = (byte) 152; // 读选定时间范围的电能累计量数据终端设备的事件信息

    public final byte Dlt719_Type_C_EVENT_NA_2 = (byte) 153; // 读电能量计量设备的事件信息

    public final byte Dlt719_Type_C_LOAD_NA_2 = (byte) 154; // 读一个选定的时间范围和一个指定的地址的装置分时数据

    public final byte Dlt719_Type_C_DT_NA_2 = (byte) 155; // 读装置分时数据时间

    public final byte Dlt719_Type_C_VER_NA_2 = (byte) 156; // 读模块版本信息

    public final byte Dlt719_Type_C_NET_NA_2 = (byte) 157; // 设置装置网络配置

    public final byte Dlt719_Type_C_NET_NB_2 = (byte) 158; // 读装置网络配置

    public final byte Dlt719_Type_C_SET_NA_2 = (byte) 159; // 读装置分时设置

    public final byte Dlt719_Type_C_MAXLS_NA_2 = (byte) 160; // 读电能累计量最大信息体地址

    public final byte Dlt719_Type_M_REG_NA_2 = (byte) 170; // 通道数据帧

    public final byte Dlt719_Type_M_EVENT_NA_2 = (byte) 172; // 读选定时间范围的电能累计量数据终端设备事件信息数据帧

    public final byte Dlt719_Type_M_EVENT_NB_2 = (byte) 173; // 电能计量设备事件信息数据帧

    public final byte Dlt719_Type_M_LOAD_TA_2 = (byte) 174; // 一个选定时间范围和一个指定地址的装置分时数据帧

    public final byte Dlt719_Type_M_DT_TA_2 = (byte) 175; // 读装置分时数据时间返回帧（M_DT_TA_2）

    public final byte Dlt719_Type_M_VER_NA_2 = (byte) 176; // 读模块版本信息返回帧（M_VER_NA_2

    public final byte Dlt719_Type_M_NET_NB_2 = (byte) 178; // 读装置网络配置返回帧（M_NET_NB_2）

    public final byte Dlt719_Type_M_SET_NA_2 = (byte) 179; // 读装置分时设置返回帧（M_SET_NA_2）

    public final byte Dlt719_Type_M_MAXLS_NA_2 = (byte) 180; // 读电能累计量最大信息体地址返回帧（M_MAXLS_NA_2）
    // -- End 科立 ---

    public final byte Dlt719_Type_S_RD_RA_2 = (byte) 200; // 读1个选定的时间范围和1个选定的地址范围的月费率行码电量

    public final byte Dlt719_Type_S_RD_RB_2 = (byte) 201; // 读1个选定的时间范围和1个选定的地址范围的月费率增量电量

    public final byte Dlt719_Type_S_RD_RC_2 = (byte) 202; // 读1个选定的时间范围和1个选定的地址范围的月最大需量

    public final byte Dlt719_Type_S_RD_RD_2 = (byte) 203; // 读1个选定的时间范围和1个选定的地址范围的功率

    public final byte Dlt719_Type_S_RD_RE_2 = (byte) 204; // 读1个选定的时间范围和1个选定的地址范围的功率因数

    public final byte Dlt719_Type_S_RD_RF_2 = (byte) 205; // 读1个选定的时间范围和1个选定的地址范围的电流

    public final byte Dlt719_Type_S_RD_RG_2 = (byte) 206; // 读1个选定的时间范围和1个选定的地址范围的电压

    public final byte Dlt719_Type_S_RD_RH_2 = (byte) 207; // 读1个选定的时间范围和1个选定的地址范围的失压记录

    public final byte Dlt719_Type_S_RD_RI_2 = (byte) 208; // 读1个指定地址范围的当前累计电量

    public final byte Dlt719_Type_S_RD_RJ_2 = (byte) 209; // 读1个指定地址范围的当月最大需量

    public final byte Dlt719_Type_S_RD_RK_2 = (byte) 210; // 读1个指定地址范围的当前功率

    public final byte Dlt719_Type_S_RD_RL_2 = (byte) 211; // 读1个指定地址范围的当前功率因数

    public final byte Dlt719_Type_S_RD_RM_2 = (byte) 212; // 读1个指定地址范围的当前电流

    public final byte Dlt719_Type_S_RD_RN_2 = (byte) 213; // 读1个指定地址范围的当前电压

    public final byte Dlt719_Type_S_RD_RO_2 = (byte) 214; // 读1个指定地址范围的当前失压记录

    public final byte Dlt719_Type_End = (byte) 255;
}
