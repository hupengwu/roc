
package com.huawei.roc.restlike;

/**
 * RESTFul风格的上下文
 * RESTFul三要素：方法/资源/参数
 * 
 * @author h00442047
 * @since 2020年1月17日
 */
public class RESTfulContextVO {
    /**
     * 动作：POST/PUT/GET/DELETE
     */
    private String method;

    /**
     * 资源标识
     */
    private String resource;

    /**
     * 参数：最好使用Map做对象
     */
    private Object param;

    /**
     * 出错码：200代表正常
     */
    private Integer error;

    /**
     * 异常信息
     */
    private Object exception;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}