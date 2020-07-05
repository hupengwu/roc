
package com.huawei.roc.protocol.dlt719.def;

//记录地址(Record addressRAD) 1byte
public class Dlt719RAD {

    public final byte Dlt719_RAD_Default = 0;   // 缺省

    public final byte Dlt719_RAD_ITRecAdd = 1;   // 从记账(计费)时段开始的电能量的记录地址

    // (中调规定了"11为1分钟、12为15分钟")
    public final byte Dlt719_RAD_ITRecAdd15M = 11;  // 电能量的记录地址(15分钟间隔)

    public final byte Dlt719_RAD_ITRecAdd01M = 12;  // 电能量的记录地址(1分钟间隔)

    public final byte Dlt719_RAD_ITRecAdd30M = 13;  // 电能量的记录地址(30分钟间隔)

    // 14、15为为扩充
    public final byte Dlt719_RAD_ITRecAdd05M = 14;  // 电能量的记录地址(5分钟间隔)

    public final byte Dlt719_RAD_ITRecAdd60M = 15;  // 电能量的记录地址(60分钟间隔)

    public final byte Dlt719_RAD_CurRecAdd = 40;  // 当前值的记录地址(不包括费率数据)

    public final byte Dlt719_RAD_MonRecAdd = 41;  // 月值的记录地址(不包括费率数据)

    public final byte Dlt719_RAD_PoitInfEarlist = 50;  // 最早的单点信息

    public final byte Dlt719_RAD_PoitInfAll = 51;  // 单点信息的全部记录

    public final byte Dlt719_RAD_PoitInfSec1 = 52;  // 单点信息记录区段1

    public final byte Dlt719_RAD_PoitInfSec2 = 53;  // 单点信息记录区段2

    public final byte Dlt719_RAD_PoitInfSec3 = 54;  // 单点信息记录区段3

    public final byte Dlt719_RAD_PoitInfSec4 = 55;  // 单点信息记录区段4

    // 以下为扩充 (记录区段的大小是1个系统参数)
    public final byte Dlt719_RAD_RateAddMonAll = 64;  // 月费率电量(总)的记录地址

    public final byte Dlt719_RAD_RateAddMonTine = 65;  // 月费率电量(尖)的记录地址

    public final byte Dlt719_RAD_RateAddMonPeak = 66;  // 月费率电量(峰)的记录地址

    public final byte Dlt719_RAD_RateAddMonLevel = 67;  // 月费率电量(平)的记录地址

    public final byte Dlt719_RAD_RateAddMonLow = 68;  // 月费率电量(谷)的记录地址

    // 以下记录地址对"遥测量、失压记录"的类型标识有效
    public final byte Dlt719_RAD_GetAdd01M = (byte) 180; // 1分钟采集间隔的记录地址

    public final byte Dlt719_RAD_GetAdd05M = (byte) 181; // 5分钟采集间隔的记录地址

    public final byte Dlt719_RAD_GetAdd15M = (byte) 182; // 15分钟采集间隔的记录地址

    public final byte Dlt719_RAD_GetAdd30M = (byte) 183; // 30分钟采集间隔的记录地址

    public final byte Dlt719_RAD_GetAdd60M = (byte) 184; // 60分钟采集间隔的记录地址

    public final byte Dlt719_RAD_End = (byte) 255;
}
