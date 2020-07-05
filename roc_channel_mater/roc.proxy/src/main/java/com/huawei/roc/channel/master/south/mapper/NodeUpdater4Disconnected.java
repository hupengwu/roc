
package com.huawei.roc.channel.master.south.mapper;

import com.huawei.roc.channel.master.data.nodemgr.Node;
import com.huawei.roc.keyvaluemapper.Writer;

public class NodeUpdater4Disconnected extends Writer {
    private String key;

    public NodeUpdater4Disconnected(String key) {
        this.key = key;
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
        node.setSocketChannel(null);

        return true;
    }
}
