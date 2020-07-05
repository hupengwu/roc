/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.frame.socket.mapper;

import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.huawei.roc.frame.constant.Escape;
import com.huawei.roc.keyvaluemapper.Writers;
import com.huawei.roc.utils.EscapeUtils;

/**
 * 收集数据已经有结束符的缓存
 * 
 * @author h00442047
 * @since 2019年12月31日
 */
public class SocketGether4GroupBuff extends Writers {
    /**
     * 输出数据
     */
    private Map<SocketChannel, List<byte[]>> data;

    public SocketGether4GroupBuff(Map<SocketChannel, List<byte[]>> data) {
        this.data = data;
    }

    @Override
    public boolean writeValue(Object key, Object value) {
        if (value == null) {
            return false;
        }

        @SuppressWarnings("unchecked")
        List<byte[]> list = (List<byte[]>) value;

        // 遍历：是否存在结束标记的数据块
        byte[] find = null;
        Iterator<byte[]> it = list.iterator();
        while (it.hasNext()) {
            byte[] em = it.next();
            if (EscapeUtils.findEscPos(em, Escape.end) != -1) {
                find = em;
                break;
            }
        }

        // 如果存在，则取出前面的数据
        if (find != null) {
            this.data.put((SocketChannel) key, new LinkedList<byte[]>());

            it = list.iterator();
            while (it.hasNext()) {
                byte[] em = it.next();
                it.remove();

                this.data.get(key).add(em);
                if (find == em) {
                    return true;
                }
            }
        }

        return true;
    }
}