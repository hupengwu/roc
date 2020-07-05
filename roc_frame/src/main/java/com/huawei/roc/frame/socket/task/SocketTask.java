
package com.huawei.roc.frame.socket.task;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.alibaba.fastjson.JSON;
import com.huawei.roc.frame.constant.Escape;
import com.huawei.roc.frame.socket.mapper.SocketGether4GroupBuff;
import com.huawei.roc.keyvaluemapper.KeyValueMapper;
import com.huawei.roc.restlike.RESTFulPorxy;
import com.huawei.roc.restlike.RESTFulResponder;
import com.huawei.roc.restlike.RESTfulContextVO;
import com.huawei.roc.utils.EscapeUtils;

public class SocketTask {

    private RESTFulPorxy proxy;

    /**
     * Json报文队列
     */
    private ConcurrentLinkedQueue<Map<String, Object>> jsonQueue;

    private ConcurrentLinkedQueue<Map<SocketChannel, String>> respondQueue;

    public RESTFulPorxy getProxy() {
        return proxy;
    }

    public void setProxy(RESTFulPorxy proxy) {
        this.proxy = proxy;
    }

    public ConcurrentLinkedQueue<Map<SocketChannel, String>> getRespondQueue() {
        return respondQueue;
    }

    public void setRespondQueue(ConcurrentLinkedQueue<Map<SocketChannel, String>> respondQueue) {
        this.respondQueue = respondQueue;
    }

    /**
     * socket容器
     */
    private KeyValueMapper socketMapper;

    public ConcurrentLinkedQueue<Map<String, Object>> getJsonQueue() {
        return jsonQueue;
    }

    public void setJsonQueue(ConcurrentLinkedQueue<Map<String, Object>> jsonQueue) {
        this.jsonQueue = jsonQueue;
    }

    public KeyValueMapper getSocketMapper() {
        return socketMapper;
    }

    public void setSocketMapper(KeyValueMapper socketMapper) {
        this.socketMapper = socketMapper;
    }

    /**
     * 是否存在结束转义字符
     * @param buff
     * @return
     */
    public static boolean hasEscEnd(byte[] buff) throws Exception {
        return EscapeUtils.findEscPos(buff, Escape.end) != -1;
    }

    /**
     * 从缓存中取出分组缓存，转换成字符串
     */
    public void makeGroupBuff2Json() {
        if (this.jsonQueue == null) {
            return;
        }
        if (this.socketMapper == null) {
            return;
        }

        try {
            // 取出已经含有分组结尾表示的缓存
            Map<SocketChannel, List<byte[]>> data = new HashMap<SocketChannel, List<byte[]>>();
            this.socketMapper.foreach(new SocketGether4GroupBuff(data));

            if (data.isEmpty()) {
                return;
            }

            for (Entry<SocketChannel, List<byte[]>> entry : data.entrySet()) {
                SocketChannel sc = entry.getKey();
                List<byte[]> list = entry.getValue();

                // 转义处理
                byte[] byteArray = EscapeUtils.unescapeByteArray(list,
                    Escape.esc,
                    Escape.begin,
                    Escape.end,
                    Escape.escEsc,
                    Escape.escBegin,
                    Escape.escEnd);

                // 还原成Json报文
                String json = new String(byteArray);
                if (json.isEmpty()) {
                    return;
                }

                // 消息结构
                Map<String, Object> message = new HashMap<String, Object>();
                message.put("json", json);
                message.put("channel", sc);

                // 放入消息队列
                this.jsonQueue.offer(message);
            }

        } catch (Exception e) {
            e.toString();
        }
    }

    public void recvJson2Vo() {

        while (true) {
            // 取出一个消息
            Map<String, Object> message = jsonQueue.poll();
            if (message == null) {
                return;
            }

            try {
                String json = (String) message.get("json");
                SocketChannel channel = (SocketChannel) message.get("channel");
                if (json == null || channel == null) {
                    return;
                }

                RESTfulContextVO requestVO = JSON.parseObject(json, RESTfulContextVO.class);
                RESTfulContextVO respondVO = new RESTfulContextVO();
                respondVO.setResource(requestVO.getResource());
                respondVO.setMethod(requestVO.getMethod());

                RESTFulResponder responder = proxy.getResponder(requestVO.getResource(), requestVO.getMethod());
                if (responder != null) {
                    String bodyParam = "";
                    if (requestVO.getParam() != null) {
                        bodyParam = requestVO.getParam().toString();
                    }

                    Map<String, Object> mapParam = new HashMap<String, Object>();
                    try {
                        mapParam = JSON.parseObject(requestVO.getParam().toString());
                    } catch (Exception e) {
                        e.toString();
                    }

                    try {
                        respondVO.setParam(responder.invokeMethod(channel, requestVO, bodyParam, mapParam));
                        respondVO.setError(200);
                    } catch (Exception e) {
                        respondVO.setException(e.getMessage());
                        respondVO.setError(500);
                    }
                } else {
                    respondVO.setException("not found this api!");
                    respondVO.setError(404);
                }

                // 填入队列
                Map<SocketChannel, String> sc2jsn = new HashMap<SocketChannel, String>();
                sc2jsn.put(channel, JSON.toJSONString(respondVO));
                respondQueue.offer(sc2jsn);

            } catch (Exception e) {
                e.toString();
            }
        }
    }

}
