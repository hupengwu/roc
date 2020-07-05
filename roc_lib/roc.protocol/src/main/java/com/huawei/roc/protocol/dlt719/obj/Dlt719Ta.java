
package com.huawei.roc.protocol.dlt719.obj;

/**
 * 结构化时间信息a(Time information a,分至年)
 * Time information a：＝CP40｛minute, TIS, IV,hour, RESI, SU,day of month,day of week,month, EIT, PTI,year, RES2｝
 * 
 * @author h00442047
 * @since 2020年1月15日
 */
public class Dlt719Ta {
    private byte minute = 0;     // 分(minute)：＝UI6［1..6］〈0..59〉

    private byte tis = 0;        // 费率信息开关(TIS＝tariff information switch)：＝BS1［7］
                                 // 〈0〉：＝费率陈述断开OFF〈1〉：＝费率陈述合上ON

    private byte iv = 0;         // 无效(IV＝invalid):＝BS1[8] <0>:＝时间陈述有效<1>:＝时间陈述无效

    private byte hour = 0;       // 小时(hour):＝UI5［9..13］〈0..23〉

    private byte res1 = 0;       // 备用1(RES1＝reserve 1):＝BS2［14..15］〈0〉

    private byte su = 0;         // 夏季时间(SU＝summer time):＝BS1［16］
                                 // 〈0〉：＝标准时间〈1〉：＝夏季时间或夏令时

    private byte dayOfMonth = 0; // 每月的某天(day of month):＝UI5［17..21］〈1..31〉

    private byte dayOfWeek = 0;  // 每星期的某天(day of week):＝UI3［22..24］〈1..7〉

    private byte month = 0;      // 月(month):＝UI4［25..28］〈1..12〉

    private byte eti = 0;        // 能量费率信息(ETI＝energy tariff information):＝UI2[29..30]<0.. 3>(每个系统任选)
                                 // <0>:＝费率1 <1>:＝费率2 <2>:＝费率3 <3>:＝费率4

    private byte pti = 0;        // 功率费率信息(PTI＝power tariff information):＝UI2[31..32]<0..3>(每个系统任选)
                                 // <0>:＝费率1 <1>:＝费率2 <2>:＝费率3 <3>:＝费率4

    private byte year = 0;       // 年(year):＝UI7［33..39］〈0..99〉

    private byte res2 = 0;       // 备用2(RES2＝reserve 2):＝BS1［40］〈0〉

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

    public byte getSu() {
        return su;
    }

    public void setSu(byte su) {
        this.su = su;
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

    public byte getEti() {
        return eti;
    }

    public void setEti(byte eti) {
        this.eti = eti;
    }

    public byte getPti() {
        return pti;
    }

    public void setPti(byte pti) {
        this.pti = pti;
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
