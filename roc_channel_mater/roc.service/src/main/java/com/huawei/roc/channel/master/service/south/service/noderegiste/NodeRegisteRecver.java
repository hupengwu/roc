
package com.huawei.roc.channel.master.service.south.service.noderegiste;

import com.alibaba.fastjson.JSON;
import com.huawei.roc.channel.master.data.MapperManager;
import com.huawei.roc.channel.master.service.south.service.nodeactivite.mapper.NodeUpdater4ActivateRsp;
import com.huawei.roc.channel.master.vo.south.NodeVO;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.restlike.RESTfulContextVO;
import com.huawei.roc.restlike.RESTfulResult;
import com.huawei.roc.utils.IsNullOrEmptyUtil;

public class NodeRegisteRecver implements INodeRegisteRecver {
    @Override
    public Object getRegisterInfo(Object channel, RESTfulContextVO context) {
        // 检查：是否为异常返回
        if (IsNullOrEmptyUtil.hasNull(channel, context, context.getError(), context.getParam())) {
            return null;
        }

        if (!context.getError().equals(RESTfulResult.DEFAULT_SUCCESS_CODE)) {
            return null;
        }

        // 将返回内容转换成RESTfulResult对象
        RESTfulResult<?> result = JSON.toJavaObject((JSON) context.getParam(), RESTfulResult.class);
        if (result.getData() == null) {
            return null;
        }

        // 将数据转成NodeVO对象
        NodeVO nodeVO = JSON.toJavaObject((JSON) result.getData(), NodeVO.class);

        try {
            // 刷新心跳响应时间
            KeyValueMapper nodeContainer = MapperManager.getNodeContainer();
            nodeContainer.update(
                new NodeUpdater4ActivateRsp(nodeVO.getNodeId(), nodeVO.getCfgFlag(), System.currentTimeMillis()));
        } catch (Exception e) {
            e.toString();
        }

        return nodeVO;
    }
}
