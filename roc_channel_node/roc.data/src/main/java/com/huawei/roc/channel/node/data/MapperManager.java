/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.channel.node.data;

import com.huawei.roc.keyvaluemapper.KeyValueMapper;

/**
 * Mapper型的管理器
 * @author h00442047
 * @since 2019年12月17日
 */
public class MapperManager {
    /**
     *  1.客户端连接：下面挂有数据块缓存
     */
    private static KeyValueMapper serverSocketContainer = new KeyValueMapper();

    private static KeyValueMapper masterSocketContainer = new KeyValueMapper();

    /**
     * 2.客户端结构化消息请求：结构化的消息
     */
    private static KeyValueMapper requestContainer = new KeyValueMapper();

    public static KeyValueMapper getRequestContainer() {
        return requestContainer;
    }

    public static KeyValueMapper getServerSocketContainer() {
        return serverSocketContainer;
    }

    public static KeyValueMapper getMasterSocketContainer() {
        return masterSocketContainer;
    }
}
