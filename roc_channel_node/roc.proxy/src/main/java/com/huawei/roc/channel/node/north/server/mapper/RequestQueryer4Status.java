
package com.huawei.roc.channel.node.north.server.mapper;

import com.huawei.roc.channel.node.vo.constant.Status;
import com.huawei.roc.channel.node.vo.server.ConnectVO;
import com.huawei.roc.channel.node.vo.server.RequestVO;
import com.huawei.roc.channel.node.vo.server.RespondVO;
import com.huawei.roc.keyvaluemapper.Reader;

public class RequestQueryer4Status extends Reader {
    /**
     * key
     */
    private String key = "";

    private RespondVO rsp = new RespondVO();

    /**
     * 获取key
     * @return 元素的key
     */
    public Object getKey() {
        return this.key;
    }

    public RespondVO getRsp() {
        return rsp;
    }

    public void setRsp(RespondVO rsp) {
        this.rsp = rsp;
    }

    public RequestQueryer4Status(String key) {
        super();
        this.key = key;
    }

    /**
     * 找到目标对象，并直接修改元素的内容
     * @param value 元素
     * @return 是否修改成功
     */
    public boolean readValue(Object value) {
        if (value == null) {
            return false;
        }

        RequestVO req = (RequestVO) value;
        ConnectVO connect = req.getConnect();

        // 尚未发送:等待状态
        if (connect.getSendTime() == null) {
            rsp.setStatus(Status.waited);
            return true;
        }
        // 尚未发送:已经发送，设备尚未回应
        if (connect.getRecvTime() == null) {
            rsp.setStatus(Status.sended);
            return true;
        }
        // 已经回应：设备已经回应
        if (connect.getRecvTime() != null) {
            rsp.setStatus(Status.recved);
            rsp.setReceiptKey(req.getReceiptKey());
            rsp.setClientId(req.getClientId());
            rsp.setRequestParam(req.getRequestParam());
            rsp.setRespondParam(req.getRespondParam());
            rsp.setConnect(connect);
            return true;
        }

        return true;
    }

}
