/*
 * Copyright (c) Huawei Technologies Co., Ltd. 2012-2019. All rights reserved.
 */

package com.huawei.roc.restlike;

public final class RESTfulResult<T> {

    /** 默认成功码 */
    public static final int DEFAULT_SUCCESS_CODE = 200;

    /** 默认失败码 */
    public static final int DEFAULT_ERROR_CODE = 0;

    /** 结果码，默认成功 */
    private int code = DEFAULT_SUCCESS_CODE;

    /** 消息 */
    private String message;

    /** 数据对象 */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 构造方法
     */
    public RESTfulResult() {

    }

    /**
     * 构造方法
     * @param code 结果码
     */
    public RESTfulResult(int code) {
        this.code = code;
    }

    /**
     * 构造方法
     * @param code 结果码
     * @param message 消息
     * @param data 数据对象
     */
    public RESTfulResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 构建返回结果对象
     * @param message 消息
     * @param data 数据对象
     * @param <K> 类型
     * @return 返回结果对象
     */
    public static <T> RESTfulResult<T> success(String message, T data) {
        RESTfulResult<T> messageResult = new RESTfulResult<T>();
        messageResult.setCode(DEFAULT_SUCCESS_CODE);
        messageResult.setMessage(message);
        messageResult.setData(data);
        return messageResult;
    }

    /**
     * 构建返回结果对象
     * @param data 数据对象
     * @param <K> 类型
     * @return 返回结果对象
     */
    public static <T> RESTfulResult<T> success(T data) {
        RESTfulResult<T> messageResult = new RESTfulResult<T>();
        messageResult.setMessage("success");
        messageResult.setCode(DEFAULT_SUCCESS_CODE);
        messageResult.setData(data);
        return messageResult;
    }

    /**
     * 构建返回结果对象
     * @param message 消息
     * @return 返回结果对象
     */
    public static RESTfulResult<Object> error(String message) {
        RESTfulResult<Object> messageResult = new RESTfulResult<Object>();
        messageResult.setMessage(message);
        messageResult.setCode(DEFAULT_ERROR_CODE);
        return messageResult;
    }

    /**
     * 构建返回结果对象
     * @param message 消息
     * @return 返回结果对象
     */
    public static RESTfulResult<Object> error(String message, int code) {
        RESTfulResult<Object> messageResult = new RESTfulResult<Object>();
        messageResult.setMessage(message);
        messageResult.setCode(code);
        return messageResult;
    }

    /**
     * 是否成功
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return DEFAULT_SUCCESS_CODE == code;
    }

    /**
     * 是否成功
     * @param value 成功参数
     */
    public void setSuccess(Boolean value) {
        if (value) {
            setCode(DEFAULT_SUCCESS_CODE);
        } else {
            setCode(DEFAULT_ERROR_CODE);
        }
    }
}
