
package com.huawei.roc.frame.socket.mapper;

import java.nio.channels.SocketChannel;
import java.util.List;

import com.huawei.roc.keyvaluemapper.Writer;

public class SocketUpdater4AddBuff extends Writer {
    /**
     * key
     */
    private SocketChannel key;

    private byte[] buff;

    /**
     * 获取key
     * @return 元素的key
     */
    public Object getKey() {
        return this.key;
    }

    public SocketUpdater4AddBuff(SocketChannel key, byte[] buff) {
        super();
        this.key = key;
        this.buff = buff;
    }

    /**
     * 找到目标对象，并直接修改元素的内容
     * @param value 元素
     * @return 是否修改成功
     */
    public boolean writeValue(Object value) {
        if (value == null || buff == null) {
            return false;
        }

        @SuppressWarnings("unchecked")
        List<byte[]> list = (List<byte[]>) value;
        list.add(this.buff);

        return true;
    }
}
