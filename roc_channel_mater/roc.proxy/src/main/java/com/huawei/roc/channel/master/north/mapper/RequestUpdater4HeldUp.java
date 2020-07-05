
package com.huawei.roc.channel.master.north.mapper;

import com.huawei.roc.channel.master.vo.north.ConnectVO;
import com.huawei.roc.channel.master.vo.north.RequestVO;
import com.huawei.roc.keyvaluemapper.Writer;

/**
 * 更新滞留期：指明已经完成任务了，开始登记为滞留状态了
 * @author h00442047
 *
 */
public class RequestUpdater4HeldUp extends Writer {
    /**
     * key
     */
    private String key;

    /**
     * 滞留开始时间
     */
    private long heldUpBegin;

    /**
     * 滞留的允许时间
     */
    private long heldUpInterval;

    /**
     * 构造函数
     * @param key 要操作的对象key
     */
    /**
     * 
     * @param key
     * @param heldUpBegin 滞留开始时间
     * @param heldUpInterval 滞留的允许时间
     */
    public RequestUpdater4HeldUp(String key, long heldUpBegin, long heldUpInterval) {
        super();
        this.key = key;
        this.heldUpBegin = heldUpBegin;
        this.heldUpInterval = heldUpInterval;
    }

    /**
     * 获取key
     * @return 元素的key
     */
    public Object getKey() {
        return this.key;
    }

    /**
     * 找到目标对象，并直接修改元素的内容
     * @param value 元素
     * @return 是否修改成功
     */
    public boolean writeValue(Object value) {
        RequestVO requestVO = RequestVO.class.cast(value);
        if (requestVO == null) {
            return false;
        }
        if (requestVO.getConnect() == null) {
            requestVO.setConnect(new ConnectVO());
        }
        ConnectVO connectVO = requestVO.getConnect();
        connectVO.setHeldUpBegin(heldUpBegin);
        connectVO.setHeldUpInterval(heldUpInterval);
        return true;
    }

}
