
package com.huawei.roc.channel.node.north.server.mapper;

import java.util.HashSet;
import java.util.Set;

import com.huawei.roc.channel.node.vo.server.ConnectVO;
import com.huawei.roc.channel.node.vo.server.RequestVO;
import com.huawei.roc.keyvaluemapper.Readers;

/**
 * 滞留期遍历：通过遍历查找超过滞留期，仍然未被客户端取走的数据
 * @author h00442047
 *
 */
public class RequestQueryer4HeldUp extends Readers {
    /**
     * key
     */
    private Set<Object> keys = new HashSet<Object>();

    /**
     * 当前时间
     */
    private long currentTime = 0;

    /**
     * 构造函数
     * @param currentTime 当前时间
     * @param span 时间价格
     */
    public RequestQueryer4HeldUp(long currentTime) {
        super();
        this.currentTime = currentTime;
    }

    @Override
    public boolean readValue(Object key, Object value) {
        RequestVO requestVO = RequestVO.class.cast(value);
        if (requestVO.getConnect() == null) {
            return false;
        }

        ConnectVO connectVO = requestVO.getConnect();

        // 滞留报文：请求处理已经结束（设备已经响应或者无法响应时），客户端依然长时间未来取的数据
        Long heldUpBegin = connectVO.getHeldUpBegin();
        Long heldUpInterval = connectVO.getHeldUpInterval();
        if ((heldUpBegin != null && heldUpInterval != null) && (heldUpBegin + heldUpInterval > currentTime)) {
            keys.add(key);
            return true;
        }

        return false;
    }

    /**
     * 获取Key
     * @return
     */
    public Set<Object> getKeys() {
        return keys;
    }
}
