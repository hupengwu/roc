
package com.huawei.roc.protocol.dlt719.obj;

/**
 * 结构化时间信息b(Time information b,毫秒至年)
 * Time information b:＝CP56｛millisecond, second, minute, TIS, IV, hour, RES1,SU，day of month, day of week, month, EIT, PTI, year, RES2｝
 * 
 * @author h00442047
 * @since 2020年1月15日
 */
public class Dlt719Tb {
    private short millisecond = 0;  // 毫秒(millisecond):＝UI10［1..10］〈0..999〉

    private byte second = 0;      // 秒(second):＝UI6［11..16］〈0..59〉

    private byte minute = 0;      // 分(minute):＝UI6［17.22］〈0..59〉

    private byte tis = 0;         // 费率信息开关(TIS＝tariff information switch):＝BS1［23］
                                  // 〈0〉：＝费率陈述断开OFF〈1〉：＝费率陈述合上ON

    private byte iv = 0;          // 无效(IV＝invalid):＝BS1［24］
                                  // 〈0〉：＝时间陈述有效〈1〉：＝时间陈述无效

    private byte hour = 0;        // 小时(hour):＝UI5［25..29］〈0..23〉

    private byte res1 = 0;        // 备用1(RES1＝reserve 1):＝BS2［30..31］〈0〉

    private byte dayOfMonth = 0;  // 每月的某天(day of month):＝UI5［33..37］〈1..31〉

    private byte dayOfWeek = 0;   // 每星期的某天(day of week):＝UI3［38..40］〈1..7〉

    private byte month = 0;       // 月(month):＝UI4［41..44］〈1..12〉

    private byte year = 0;        // 年(year):＝UI7［49..55］〈0..99〉

    private byte res2 = 0;        // 备用2(RES2＝RESERVE2):＝BS1［56］〈0〉

    public short getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(short millisecond) {
        this.millisecond = millisecond;
    }

    public byte getSecond() {
        return second;
    }

    public void setSecond(byte second) {
        this.second = second;
    }

    public byte getMinute() {
        return minute;
    }

    public void setMinute(byte minute) {
        this.minute = minute;
    }

    public byte getTis() {
        return tis;
    }

    public void setTis(byte tis) {
        this.tis = tis;
    }

    public byte getIv() {
        return iv;
    }

    public void setIv(byte iv) {
        this.iv = iv;
    }

    public byte getHour() {
        return hour;
    }

    public void setHour(byte hour) {
        this.hour = hour;
    }

    public byte getRes1() {
        return res1;
    }

    public void setRes1(byte res1) {
        this.res1 = res1;
    }

    public byte getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(byte dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public byte getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(byte dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public byte getMonth() {
        return month;
    }

    public void setMonth(byte month) {
        this.month = month;
    }

    public byte getYear() {
        return year;
    }

    public void setYear(byte year) {
        this.year = year;
    }

    public byte getRes2() {
        return res2;
    }

    public void setRes2(byte res2) {
        this.res2 = res2;
    }

}
