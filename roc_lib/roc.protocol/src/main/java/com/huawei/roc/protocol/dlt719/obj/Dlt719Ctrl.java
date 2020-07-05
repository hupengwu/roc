
package com.huawei.roc.protocol.dlt719.obj;

/**
 * 结构化的控制域
 * 
 * @author h00442047
 * @since 2020年1月15日
 */
public class Dlt719Ctrl {
    private byte dir = 0;       // 传输方向位 / 备用

    private byte prm = 0;       // 启动报文位 / 0

    private byte fcbAcd = 0;    // 帧计数位 / 要求访问位

    private byte favDfc = 0;    // 帧计数有效位 / 数据流控制位

    private byte fc = 0;        // 功能码

    public byte getDir() {
        return dir;
    }

    public void setDir(byte dir) {
        this.dir = dir;
    }

    public byte getPrm() {
        return prm;
    }

    public void setPrm(byte prm) {
        this.prm = prm;
    }

    public byte getFcbAcd() {
        return fcbAcd;
    }

    public void setFcbAcd(byte fcbAcd) {
        this.fcbAcd = fcbAcd;
    }

    public byte getFavDfc() {
        return favDfc;
    }

    public void setFavDfc(byte favDfc) {
        this.favDfc = favDfc;
    }

    public byte getFc() {
        return fc;
    }

    public void setFc(byte fc) {
        this.fc = fc;
    }
}
