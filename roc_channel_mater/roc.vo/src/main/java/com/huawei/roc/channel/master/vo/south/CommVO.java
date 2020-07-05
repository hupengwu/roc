
package com.huawei.roc.channel.master.vo.south;

import java.util.Map;

/**
 * 通讯对象
 * @author h00442047
 * @since 2019年12月31日
 */
public class CommVO {
    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型：指明是何种通信类型，即指明param的结构
     */
    private String type;

    /**
     * 具体的通信参数
     */
    private Map<String, Object> param;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}
