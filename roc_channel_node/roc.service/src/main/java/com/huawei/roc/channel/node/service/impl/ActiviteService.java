
package com.huawei.roc.channel.node.service.impl;

import com.alibaba.fastjson.JSON;
import com.huawei.roc.channel.master.vo.south.NodeVO;
import com.huawei.roc.channel.node.data.ObjectManager;
import com.huawei.roc.channel.node.service.IActiviteService;
import com.huawei.roc.restlike.RESTfulResult;

public class ActiviteService implements IActiviteService {
    @Override
    public Object getActivateInfo(String bodyJson) {
        NodeVO nodeVO = JSON.parseObject(bodyJson, NodeVO.class);

        if (nodeVO.getNodeId() == null || nodeVO.getHeartbeat() == null) {
            return RESTfulResult.error("param error");
        }

        nodeVO.setCfgFlag(ObjectManager.instance().getNodeCfgFlag());
        nodeVO.setNodeId(ObjectManager.instance().getNodeId());

        return RESTfulResult.success(nodeVO);
    }
}
