
package com.huawei.roc.channel.master.south.mapper;

import com.huawei.roc.channel.master.data.nodemgr.Node;
import com.huawei.roc.keyvaluemapper.Writer;

public class NodeUpdater4HasTryConnect extends Writer {
    private String key;

    private long currentTime;

    public NodeUpdater4HasTryConnect(String key, long currentTime) {
        this.key = key;
        this.currentTime = currentTime;

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
        if (value == null) {
            return false;
        }

        Node node = (Node) value;
        node.setConnectTime(currentTime);

        return true;
    }
}
