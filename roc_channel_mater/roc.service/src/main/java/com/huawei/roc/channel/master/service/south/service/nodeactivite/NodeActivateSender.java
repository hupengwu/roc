
package com.huawei.roc.channel.master.service.south.service.nodeactivite;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.huawei.it.eip.apigw.client.Http;
import com.huawei.roc.channel.master.SocketManager;
import com.huawei.roc.channel.master.data.MapperManager;
import com.huawei.roc.channel.master.service.south.service.nodeactivite.mapper.NodeQueryer4NeedActivateReq;
import com.huawei.roc.channel.master.service.south.service.nodeactivite.mapper.NodeUpdater4ActivateReq;
import com.huawei.roc.channel.master.south.mapper.NodeUpdater4Disconnected;
import com.huawei.roc.channel.master.south.mapper.NodeUpdater4HasTryConnect;
import com.huawei.roc.channel.master.vo.south.NodeVO;
import com.huawei.roc.frame.utils.EscapeUtils;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.niosocket.multithread.socket.NioClientSocket;
import com.huawei.roc.restlike.RESTfulContextVO;

/**
 * 节点激活器：定时心跳
 * 
 * @author h00442047
 * @since 2020年1月16日
 */
public class NodeActivateSender {
    /**
     * 
     */
    private static String url_act_info = "activate/info";

    /**
     * 发送心态报文
     * 功能说明：如果上次发送的心跳时间超过10秒，那么就发送心跳报文
     */
    public static void sendActivate() {
        KeyValueMapper nodeContainer = MapperManager.getNodeContainer();
        NioClientSocket southSocket = SocketManager.getSouthSocket();

        try {
            // 查找尚未建立连接的节点
            Map<String, SocketChannel> nodes = new HashMap<String, SocketChannel>();
            nodeContainer.foreach(new NodeQueryer4NeedActivateReq(System.currentTimeMillis(), 10 * 1000, nodes));

            for (Entry<String, SocketChannel> entry : nodes.entrySet()) {
                String nodeId = entry.getKey();
                SocketChannel socketChannel = entry.getValue();

                try {
                    // 检查：连接是否失效
                    if (!southSocket.isConnected(socketChannel)) {
                        // 关闭socket
                        socketChannel.close();
                        // 更新node容器中的socket属性为失效
                        nodeContainer.update(new NodeUpdater4Disconnected(nodeId));
                        continue;
                    }

                    // REST风格的请求报文：心跳
                    RESTfulContextVO restVO = new RESTfulContextVO();
                    restVO.setMethod(Http.HTTP_GET);
                    restVO.setResource(url_act_info);
                    restVO.setParam(new NodeVO());
                    NodeVO nodeVo = (NodeVO) restVO.getParam();
                    nodeVo.setNodeId(nodeId);
                    nodeVo.setHeartbeat(System.currentTimeMillis());

                    // 发出报文
                    String jsn = JSON.toJSONString(restVO);

                    // 编码成转义字符
                    byte[] outs = EscapeUtils.escapeByteArray(jsn);

                    southSocket.writeChannel(socketChannel, outs);

                    // 发出成功：更新发出时间
                    nodeContainer.update(new NodeUpdater4ActivateReq(nodeId, System.currentTimeMillis()));
                } catch (Exception e) {
                    e.toString();

                    // 连接失败：更新本次连接时间
                    nodeContainer.update(new NodeUpdater4HasTryConnect(nodeId, System.currentTimeMillis()));
                    continue;
                }

            }
        } catch (Exception e) {
            e.toString();
        }
    }

}
