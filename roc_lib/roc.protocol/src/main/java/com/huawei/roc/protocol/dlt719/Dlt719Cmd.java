
package com.huawei.roc.protocol.dlt719;

import com.huawei.roc.protocol.dlt719.obj.Dlt719Ctrl;
import com.huawei.roc.protocol.dlt719.obj.Dlt719Ta;
import com.huawei.roc.protocol.dlt719.obj.Dlt719Tb;
import com.huawei.roc.value.ByteArrayValue;
import com.huawei.roc.value.ByteValue;
import com.huawei.roc.value.ShortValue;

/**
 * Dlt719基本解析：Dlt719有可变长度和固定长度两种格式
 * 
 * @author h00442047
 * @since 2020年1月16日
 */
public class Dlt719Cmd {

    /**
     * 打包LPDUVL：可变帧长度
     * 
     * @param byCtrl 控制域
     * @param wAddr 地址域(
     * @param arrASDU 链路用户数据
     * @param arrLPDUValue LPDUVL报文
     * @return 是否成功
     */
    public static boolean packLPDUVL(byte byCtrl, short wAddr, byte[] arrASDU, ByteArrayValue arrLPDUValue) {
        // [启动字符(68H)][L][L(重复)][启动字符(68H)][控制域(C)][地址域(A)][链路用户数据(可变长度)][帧校验(CS)][结束字符(16H)]
        // 1 1 1 1 1 2 N 1 1
        // |<------ 固定长度的报文头 ------------>||<------------ L个八位位组 ----------------------->|
        // ===================================================================================================================

        int iDataSize = arrASDU.length;
        arrLPDUValue.setValue(new byte[iDataSize + 9]);
        byte[] arrLPDU = arrLPDUValue.getValue();

        int byAt = 0;

        // 起始符
        arrLPDU[byAt++] = 0x68;// (byte)VStartChar;

        // 帧长度
        arrLPDU[byAt++] = (byte) (iDataSize + 3);
        arrLPDU[byAt++] = (byte) (iDataSize + 3);

        // 起始符
        arrLPDU[byAt++] = 0x68;// (byte)VStartChar;

        // 控制码
        arrLPDU[byAt++] = byCtrl;

        // 地址域
        arrLPDU[byAt++] = (byte) (wAddr & 0xFF);
        arrLPDU[byAt++] = (byte) ((wAddr >> 8) & 0xFF);

        // 数据域
        System.arraycopy(arrASDU, 0, arrLPDU, byAt, iDataSize);
        byAt += iDataSize;

        // 校验码
        arrLPDU[byAt++] = getLPDUVLVfy(arrLPDU);

        // 结束码
        arrLPDU[byAt++] = 0x16;// (byte)VEndChar;
        return true;
    }

    /**
     * 解包LPDUVL：可变帧长度
     * 
     * @param arrLPDU 报文
     * @param byCtrlValue 控制域
     * @param wAddrValue 地址域
     * @param arrASDUValue 链路用户数据
     * @return 解码是否成功
     */
    public static boolean unpackLPDUVL(byte[] arrLPDU, ByteValue byCtrlValue, ShortValue wAddrValue,
        ByteArrayValue arrASDUValue) {
        // [启动字符(68H)][L][L(重复)][启动字符(68H)][控制域(C)][地址域(A)][链路用户数据(可变长度)][帧校验(CS)][结束字符(16H)]
        // 1 1 1 1 1 2 N 1 1
        // |<------ 固定长度的报文头 ------------>||<------------ L个八位位组 ----------------------->|
        // ===================================================================================================================

        // byte*byAt=arrLPDU.GetData();
        int byAt = 0;

        // 第一个启动字符判断// VStartChar)
        if (arrLPDU[byAt] != 0x68) {
            return false;
        }

        // 长度判断
        int iCmdSize = arrLPDU.length;
        if (iCmdSize < 9) {
            return false;
        }

        // 结束字符判断// VEndChar)
        if (arrLPDU[byAt + iCmdSize - 1] != 0x16) {
            return false;
        }

        // 第二个启动字符判断// VStartChar)
        if (arrLPDU[byAt + 3] != 0x68) {
            return false;
        }

        // 帧长度判断
        byte byLen = arrLPDU[byAt + 1];
        if (byLen != iCmdSize - 6) {
            return false;
        }
        if (byLen != arrLPDU[byAt + 2]) {
            return false;
        }

        // 帧校验
        byte vfy = getLPDUVLVfy(arrLPDU);
        if (vfy != arrLPDU[byAt + iCmdSize - 2]) {
            return false;
        }

        // 控制域
        byCtrlValue.setValue(arrLPDU[byAt + 4]);

        // 地址域
        wAddrValue.setValue((short) ((short) (arrLPDU[byAt + 5]) | ((short) arrLPDU[byAt + 6]) << 8));

        // 数据域
        arrASDUValue.setValue(new byte[byLen - 3]);
        byte[] arrASDU = arrASDUValue.getValue();
        System.arraycopy(arrLPDU, 7, arrASDU, 0, byLen - 3);

        return true;
    }

    /**
     * 计算LPDUVL验证码
     * 
     * @param arrCmd 报文
     * @return 验证码
     */
    public static byte getLPDUVLVfy(byte[] arrCmd) {
        // byte*byAt=arrCmd.GetData()+4;
        int byAt = 4;
        int iSize = arrCmd.length - 6;

        byte byVfy = 0;
        for (int i = 0; i < iSize; i++) {
            byVfy += arrCmd[byAt++];
        }

        return byVfy;
    }

    /**
     * 打包LPDUCL：固定帧长度
     * 
     * @param byCtrl 控制域
     * @param wAddr 地址域
     * @param arrLPDUValue 报文
     * @return 打包是否成功
     */
    public static boolean packLPDUCL(byte byCtrl, short wAddr, ByteArrayValue arrLPDUValue) {
        // [启动字符(10H)][控制域(C)][地址域(A)][帧校验(CS)][结束字符(16H)]
        // 1 1 2 1 1
        // =================================================================
        arrLPDUValue.setValue(new byte[6]);
        byte[] arrLPDU = arrLPDUValue.getValue();

        // byte*byAt=arrLPDU.GetData();
        int byAt = 0;

        // 起始符
        arrLPDU[byAt++] = 0x10;// (byte)FStartChar;

        // 控制码
        arrLPDU[byAt++] = byCtrl;

        // 地址域
        arrLPDU[byAt++] = (byte) (wAddr & 0xFF);
        arrLPDU[byAt++] = (byte) ((wAddr >> 8) & 0xFF);

        // 校验码
        arrLPDU[byAt++] = getLPDUVLCLVfy(arrLPDU);

        // 结束码
        arrLPDU[byAt] = 0x16;// (byte)FEndChar;

        return true;
    }

    /**
     * 解包LPDUCL：固定帧长度
     * 
     * @param arrLPDU 报文
     * @param byCtrlValue 控制域
     * @param wAddrValue 地址域
     * @return 是否解包成功
     */
    boolean unPackLPDUCL(byte[] arrLPDU, ByteValue byCtrlValue, ShortValue wAddrValue) {
        // [启动字符(10H)][控制域(C)][地址域(A)][帧校验(CS)][结束字符(16H)]
        // 1 1 2 1 1
        // =================================================================

        // 检查启动字符
        // byte*byAt=arrLPDU.GetData();
        int byAt = 0;
        if (arrLPDU[byAt] != 0x10)// FStartChar)
        {
            return false;
        }

        // 检查:数据长度
        int iCmdSize = arrLPDU.length;
        if (iCmdSize != 6) {
            return false;
        }

        // 检查结束码
        if (arrLPDU[byAt + iCmdSize - 1] != 0x16)// FEndChar)
        {
            return false;
        }

        // 控制码
        byCtrlValue.setValue(arrLPDU[byAt + 1]);

        // 地址域
        // memcpy((byte*)&wAddr,byAt+2,2);
        wAddrValue.setValue((short) ((short) (arrLPDU[byAt + 2]) | ((short) arrLPDU[byAt + 3]) << 8));

        // 检查:校验和
        if (getLPDUVLCLVfy(arrLPDU) != arrLPDU[byAt + iCmdSize - 2]) {
            return false;
        }

        return true;
    }

    /**
     * 计算LPDUVLCL验证码
     * @param arrCmd
     * @return
     */
    public static byte getLPDUVLCLVfy(byte[] arrCmd) {
        int byAt = 1;
        int iSize = arrCmd.length - 3;

        byte byVfy = 0;
        for (int i = 0; i < iSize; i++) {
            byVfy += arrCmd[byAt++];
        }

        return byVfy;
    }

    public static boolean packLPDUOL(byte[] arrLPDU) {
        return true;
    }

    public static boolean unpackLPDUOL(byte[] arrLPDU) {
        return true;
    }

    /**
     * 打包控制域
     * 
     * @param ctrl 控制域结构
     * @param byCtrl 控制域编码
     * @return 是否成功
     */
    public static boolean packCtrl(Dlt719Ctrl ctrl, byte byCtrl) {
        // 下行
        if (ctrl.getPrm() != 1) {
            return false;
        }

        if (ctrl.getDir() != 0) {
            return false;
        }

        switch (ctrl.getFc()) {
            case 0:// Dlt719_CtrlFucD_Reset:
            case 9:// Dlt719_CtrlFucD_LnState:
                if (ctrl.getFavDfc() != 0) {
                    return false;
                }
                break;
            case 3:// Dlt719_CtrlFucD_Trans:
            case 10:// Dlt719_CtrlFucD_Data1:
            case 11:// Dlt719_CtrlFucD_Data2:
                if (ctrl.getFavDfc() != 1) {
                    return false;
                }
                break;
            default:
                return false;
        }

        byCtrl = ctrl.getFc();
        byCtrl |= (ctrl.getFavDfc() << 4);
        byCtrl |= (ctrl.getFcbAcd() << 5);
        byCtrl |= (ctrl.getPrm() << 6);

        return true;
    }

    /**
     * 解包控制域
     * 
     * @param byCtrl 控制域编码
     * @param ctrl 控制结构
     * @return
     */
    public static boolean unpackCtrl(byte byCtrl, Dlt719Ctrl ctrl) {
        ctrl.setFc((byte) (byCtrl & 0x0F));
        ctrl.setFavDfc((byte) ((byCtrl >> 4) & 0x01));
        ctrl.setFcbAcd((byte) ((byCtrl >> 5) & 0x01));
        ctrl.setPrm((byte) ((byCtrl >> 6) & 0x01));
        ctrl.setDir((byte) ((byCtrl >> 7) & 0x01));

        // 上行
        if (ctrl.getPrm() != 0) {
            return false;
        }

        return true;
    }

    /**
     * 打包ASDU
     * 
     * @param byType 类型标识
     * @param byVsq 可变结构限定词
     * @param byCot 传送原因
     * @param wDevAddr 终端设备地址
     * @param byRecAddr 记录地址
     * @param arrInfo 信息体、时标
     * @param arrASDUValue 报文
     * @return 是否打包成功
     */
    public static boolean packASDU(byte byType, byte byVsq, byte byCot, short wDevAddr, byte byRecAddr, byte[] arrInfo,
        ByteArrayValue arrASDUValue) {
        // [类型标识][可变结构限定词][传送原因][终端设备地址][记录地址][信息体、时标]
        // 1 1 1 2 1 N
        // ==========================================================================
        arrASDUValue.setValue(new byte[arrInfo.length + 6]);
        byte[] arrASDU = arrASDUValue.getValue();

        // byte*byAt=arrASDU.GetData();
        int byAt = 0;

        arrASDU[byAt++] = byType;
        arrASDU[byAt++] = byVsq;
        arrASDU[byAt++] = byCot;
        arrASDU[byAt++] = (byte) (wDevAddr & 0xFF);
        arrASDU[byAt++] = (byte) ((wDevAddr >> 8) & 0xFF);
        arrASDU[byAt++] = byRecAddr;
        // memcpy(byAt, arrInfo.GetData(), arrInfo.length);
        System.arraycopy(arrInfo, 0, arrASDU, byAt, arrInfo.length);

        return true;
    }

    /**
     * 解包ASDU
     * 
     * @param arrASDU 报文
     * @param byTypeValue 类型标识
     * @param byVsqValue 可变结构限定词
     * @param byCotValue 传送原因
     * @param wDevAddrValue 终端设备地址
     * @param byRecAddrValue 记录地址
     * @param arrInfoValue 信息体、时标
     * @return 解码是否成功
     */
    public static boolean unpackASDU(byte[] arrASDU, ByteValue byTypeValue, ByteValue byVsqValue, ByteValue byCotValue,
        ShortValue wDevAddrValue, ByteValue byRecAddrValue, ByteArrayValue arrInfoValue) {
        // [类型标识][可变结构限定词][传送原因][终端设备地址][记录地址][信息体、时标]
        // 1 1 1 2 1 N
        // ==========================================================================

        if (arrASDU.length < 6) {
            return false;
        }

        // byte*byAt=arrASDU.GetData();
        int byAt = 0;

        // 类型标识(Type identification)
        byTypeValue.setValue(arrASDU[byAt]);

        // 可变结构限定词(Variable structure qualifier)
        byVsqValue.setValue(arrASDU[byAt + 1]);

        // 传送原因(Cause of transmission)
        byCotValue.setValue(arrASDU[byAt + 2]);

        // 电能量数据终端设备地址(Address of integrated total data terminal equipment)
        // memcpy((byte*)&wDevAddr,byAt+3,2);
        wDevAddrValue.setValue((short) ((short) (arrASDU[byAt + 3]) | ((short) arrASDU[byAt + 4]) << 8));

        // 记录地址(Record addressRAD)
        byRecAddrValue.setValue(arrASDU[byAt + 5]);

        // 可变数据(信息体、时标)
        arrInfoValue.setValue(new byte[arrASDU.length - 6]);
        byte[] arrInfo = arrInfoValue.getValue();
        // memcpy(arrInfo.GetData(), arrASDU.GetData() + 6, arrASDU.length - 6);
        System.arraycopy(arrASDU, 6, arrInfo, 0, arrASDU.length - 6);

        return true;
    }

    /**
     * 打包时间结构A
     * 
     * @param ta 时间结构
     * @param arrItemValue 报文编码
     * @return 是否成功
     */
    public static boolean packTimeA(Dlt719Ta ta, ByteArrayValue arrItemValue) {
        // if( ta.byMinute > 59 ||
        // (ta.byTIS != 0 && ta.byTIS != 1) ||
        // (ta.byIV != 0 && ta.byIV != 1) ||
        // ta.byHour > 23 || ta.byRES1 != 0 ||
        // (ta.bySU != 0 && ta.bySU != 1) ||
        // (ta.byDayOfMonth < 1 || ta.byDayOfMonth > 31) ||
        // (ta.byDayOfWeek < 1 || ta.byDayOfWeek > 7) ||
        // (ta.byMonth < 1 || ta.byMonth > 12) ||
        // ta.byETI > 3 ||
        // ta.byPTI > 3 ||
        // ta.byYear > 99 ||
        // ta.byRES2 != 0
        // )
        // {
        // byErr = Dlt719_Err_INFO_TimeA;
        // return false;
        // }

        arrItemValue.setValue(new byte[5]);
        byte[] arrItem = arrItemValue.getValue();
        // byte *byAt = arrItem.GetData();
        int byAt = 0;

        arrItem[byAt] = ta.getMinute();
        arrItem[byAt] |= ta.getTis() << 6;
        arrItem[byAt] |= ta.getIv() << 7;

        byAt++;
        arrItem[byAt] = ta.getHour();
        arrItem[byAt] |= ta.getRes1() << 5;
        arrItem[byAt] |= ta.getSu() << 7;

        byAt++;
        arrItem[byAt] = ta.getDayOfMonth();
        arrItem[byAt] |= ta.getDayOfWeek() << 5;

        byAt++;
        arrItem[byAt] = ta.getMonth();
        arrItem[byAt] |= ta.getEti() << 4;
        arrItem[byAt] |= ta.getPti() << 6;

        byAt++;
        arrItem[byAt] = ta.getYear();
        arrItem[byAt] |= ta.getRes2() << 7;

        return true;
    }

    /**
     * 打包时间A报文
     * 
     * @param nYear 年
     * @param byMon 月
     * @param byDay 日
     * @param byHour 时
     * @param byMinute 分
     * @param arrItemValue 报文
     * @return 是否成功
     */
    public static boolean packTimeA(int nYear, byte byMon, byte byDay, byte byHour, byte byMinute,
        ByteArrayValue arrItemValue) {
        if (nYear < 2000 || nYear > 2099 || byMon > 12 || byDay > 31 || byHour > 23 || byMinute > 59) {
            return false;
        }

        Dlt719Ta ta = new Dlt719Ta();
        // memset(&ta,0,sizeof(struct CDlt719Ta));

        ta.setYear((byte) (nYear - 2000));
        ta.setMonth(byMon);
        ta.setDayOfMonth(byDay);
        ta.setHour(byHour);
        ta.setMinute(byMinute);

        return packTimeA(ta, arrItemValue);
    }

    /**
     * 解包时间A
     * @param arrItem 报文
     * @param ta 时间A
     * @return 是否成功
     */
    public static boolean unpackTimeA(byte[] arrItem, Dlt719Ta ta) {
        if (arrItem.length != 5) {
            return false;
        }

        // byte *byAt = arrItem.GetData();
        int byAt = 0;

        ta.setMinute((byte) (arrItem[byAt] & 0x3F));              // UI6［1..6］〈0..59〉
        ta.setTis((byte) ((arrItem[byAt] >> 6) & 0x01));          // BS1［7］〈0〉〈1〉
        ta.setIv((byte) ((arrItem[byAt] >> 7) & 0x01));           // BS1［8］〈0〉〈1〉
        ta.setHour((byte) ((arrItem[byAt + 1]) & 0x1F));          // UI5［9..13］〈0..23〉
        ta.setRes1((byte) ((arrItem[byAt + 1] >> 5) & 0x03));     // BS2［14..15］〈0〉
        ta.setSu((byte) ((arrItem[byAt + 1] >> 7) & 0x01));       // BS1［16］〈0〉〈1〉
        ta.setDayOfMonth((byte) (arrItem[byAt + 2] & 0x1F));      // UI5［17..21］〈1..31〉
        ta.setDayOfWeek((byte) ((arrItem[byAt + 2] >> 5) & 0x07));// UI3［22..24］〈1..7〉
        ta.setMonth((byte) (arrItem[byAt + 3] & 0x0F));           // UI4［25..28］〈1..12〉
        ta.setEti((byte) ((arrItem[byAt + 3] >> 4) & 0x03));      // UI2［29..30］〈0.. 3〉
        ta.setPti((byte) ((arrItem[byAt + 3] >> 6) & 0x03));      // UI2［31..32］〈0..3〉
        ta.setYear((byte) (arrItem[byAt + 4] & 0x7F));            // UI7［33..39］〈0..99〉
        ta.setRes2((byte) ((arrItem[byAt + 4] >> 7) & 0x01));     // BS1［40］〈0〉

        // 发现返回的byDayOfWeek有误
        // if( ta.byMinute > 59 ||
        // (ta.byTIS != 0 && ta.byTIS != 1) ||
        // (ta.byIV != 0 && ta.byIV != 1) ||
        // ta.byHour > 23 || ta.byRES1 != 0 ||
        // (ta.bySU != 0 && ta.bySU != 1) ||
        // (ta.byDayOfMonth < 1 || ta.byDayOfMonth > 31) ||
        // (ta.byDayOfWeek < 1 || ta.byDayOfWeek > 7) ||
        // (ta.byMonth < 1 || ta.byMonth > 12) ||
        // ta.byETI > 3 ||
        // ta.byPTI > 3 ||
        // ta.byYear > 99 ||
        // ta.byRES2 != 0
        // )
        // {
        // byErr = Dlt719_Err_INFO_TimeA;
        // return false;
        // }

        return true;
    }

    /**
     * 打包时间B
     * @param tb 时间B结构
     * @param arrItemValue 报文
     * @return 是否成功
     */
    public static boolean packTimeB(Dlt719Tb tb, ByteArrayValue arrItemValue) {
        arrItemValue.setValue(new byte[7]);
        byte[] arrItem = arrItemValue.getValue();

        // byte *byAt = arrItem.GetData();
        int byAt = 0;

        arrItem[byAt] = (byte) (tb.getMillisecond() % 0x100);

        byAt++;
        arrItem[byAt] = (byte) (tb.getMillisecond() / 0x100);
        arrItem[byAt] |= tb.getSecond() << 2;

        byAt++;
        arrItem[byAt] = tb.getMinute();
        arrItem[byAt] |= tb.getTis() << 6;
        arrItem[byAt] |= tb.getIv() << 7;

        byAt++;
        arrItem[byAt] = tb.getHour();
        arrItem[byAt] |= tb.getRes1() << 5;

        byAt++;
        arrItem[byAt] = tb.getDayOfMonth();
        arrItem[byAt] |= tb.getDayOfWeek() << 5;

        byAt++;
        arrItem[byAt] = tb.getMonth();

        byAt++;
        arrItem[byAt] = tb.getYear();
        arrItem[byAt] |= tb.getRes2() << 7;

        return true;
    }

    /**
     * 打包时间B
     * 
     * @param nYear 年
     * @param byMon 月
     * @param byDay 日
     * @param byHour 时
     * @param byMinute 分
     * @param bySecond 秒
     * @param wMillisecond 毫秒
     * @param arrItemValue 报文
     * @return 是否成功
     */
    public static boolean packTimeB(int nYear, byte byMon, byte byDay, byte byHour, byte byMinute, byte bySecond,
        short wMillisecond, ByteArrayValue arrItemValue) {
        if (nYear < 2000 || nYear > 2099 || byMon > 12 || byDay > 31 || byHour > 23 || byMinute > 59 || bySecond > 59
            || wMillisecond > 999) {
            return false;
        }

        Dlt719Tb tb = new Dlt719Tb();

        // memset(&tb,0,sizeof(struct CDlt719Tb));
        tb.setYear((byte) (nYear - 2000));
        tb.setMonth(byMon);
        tb.setDayOfMonth(byDay);
        tb.setHour(byHour);
        tb.setMinute(byMinute);
        tb.setSecond(bySecond);
        tb.setMillisecond(wMillisecond);

        return packTimeB(tb, arrItemValue);

    }

    /**
     * 解包时间B
     * 
     * @param arrItem 报文
     * @param tb 时间B
     * @return 是否成功
     */
    public static boolean unpackTimeB(byte[] arrItem, Dlt719Tb tb) {
        int len = arrItem.length;
        if (len != 7) {
            return false;
        }

        // byte *byAt = arrItem.GetData();
        int byAt = 0;

        tb.setMillisecond((short) ((short) (arrItem[byAt]) | (((short) (arrItem[byAt + 1]) & 0x03) << 8))); // UI10［1..10］〈0..999〉
        tb.setSecond((byte) (((arrItem[byAt + 1]) >> 2) & 0x3F));  // UI6［11..16］〈0..59〉
        tb.setMinute((byte) ((arrItem[byAt + 2]) & 0x3F));       // UI6［17.22］〈0..59〉
        tb.setTis((byte) (((arrItem[byAt + 2]) >> 6) & 0x01));  // BS1［23］ 〈0〉〈1〉
        tb.setIv((byte) (((arrItem[byAt + 2]) >> 7) & 0x01));  // BS1［24］ 〈0〉〈1〉
        tb.setHour((byte) ((arrItem[byAt + 3]) & 0x1F));       // UI5［25..29］〈0..23〉
        tb.setRes1((byte) (((arrItem[byAt + 3]) >> 5) & 0x03));  // BS2［30..31］〈0〉
        tb.setDayOfMonth((byte) ((arrItem[byAt + 4]) & 0x1F));       // UI5［33..37］〈1..31〉
        tb.setDayOfWeek((byte) (((arrItem[byAt + 4]) >> 5) & 0x07));  // UI3［38..40］〈1..7〉
        tb.setMonth((byte) ((arrItem[byAt + 5]) & 0x0F));       // UI4［41..44］〈1..12〉
        tb.setYear((byte) ((arrItem[byAt + 6]) & 0x7F));       // UI7［49..55］〈0..99〉
        tb.setRes2((byte) (((arrItem[byAt + 6]) >> 7) & 0x01));  // BS1［56］〈0〉

        // if ( tb.wMillisecond > 999 ||
        // tb.bySecond > 59 ||
        // tb.byMinute > 59 ||
        // (tb.byTIS != 0 && tb.byTIS != 1) ||
        // (tb.byIV != 0 && tb.byIV != 1) ||
        // tb.byHour > 23 ||
        // tb.byRES1 != 0 ||
        // (tb.byDayOfmonth < 1 || tb.byDayOfmonth > 31) ||
        // (tb.byDayOfWeek < 1 || tb.byDayOfWeek > 7) ||
        // (tb.byMonth < 1 || tb.byMonth > 12) ||
        // tb.byYear > 99 ||
        // tb.byRES2 != 0
        // )
        // {
        // byErr = Dlt719_Err_INFO_TimeB;
        // return false;
        // }

        return true;
    }

    /**
     * 打包报文
     * @param byCtrl 控制域
     * @param wAddr 地址域
     * @param byType 类型标识
     * @param byVsq 可变结构限定词
     * @param byCot 传送原因
     * @param wDevAddr 终端设备地址
     * @param byRecAddr 记录地址
     * @param arrInfo
     * @param arrCmdValue
     * @return
     */
    public static boolean packLPDUVL(byte byCtrl, short wAddr, byte byType, byte byVsq, byte byCot, short wDevAddr,
        byte byRecAddr, byte[] arrInfo, ByteArrayValue arrCmdValue) {

        // 打包ASDU层数据
        ByteArrayValue arrASDUValue = new ByteArrayValue();
        if (!packASDU(byType, byVsq, byCot, wDevAddr, byRecAddr, arrInfo, arrASDUValue)) {
            return false;
        }

        // 打包LPDUVL层数据
        if (!packLPDUVL(byCtrl, wAddr, arrASDUValue.getValue(), arrCmdValue)) {
            return false;
        }

        return true;
    }

    /**
     * 解包报文
     * @param arrCmd LPDUVL层报文
     * @param byCtrlValue 控制域
     * @param wAddrValue 地址域
     * @param byTypeValue 类型标识
     * @param byVsqValue 可变结构限定词
     * @param byCotValue 传送原因
     * @param wDevAddrValue 终端设备地址
     * @param byRecAddrValue 记录地址
     * @param arrInfoValue 信息体、时标
     * @return
     */
    public static boolean unpackLPDUVL(byte[] arrCmd, ByteValue byCtrlValue, ShortValue wAddrValue,
        ByteValue byTypeValue, ByteValue byVsqValue, ByteValue byCotValue, ShortValue wDevAddrValue,
        ByteValue byRecAddrValue, ByteArrayValue arrInfoValue) {

        // 解包LPDUVL层数据
        ByteArrayValue arrASDUValue = new ByteArrayValue();
        if (!unpackLPDUVL(arrCmd, byCtrlValue, wAddrValue, arrASDUValue)) {
            return false;
        }

        // 解包ASDU层数据
        byte[] arrASDU = arrASDUValue.getValue();
        if (!unpackASDU(arrASDU, byTypeValue, byVsqValue, byCotValue, wDevAddrValue, byRecAddrValue, arrInfoValue)) {
            return false;
        }

        return true;
    }

}
