package com.mantulife.demo.utils;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * author ruoyi
 *  public R  logout(HttpServletRequest request)
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int FAIL = 1000;

    private int code;

    private String msg;

    private T data;

    public static <T> com.mantulife.common.utils.R<T> ok() {
        return restResult(null, SUCCESS, null);
    }

    public static <T> com.mantulife.common.utils.R<T> ok(T data) {
        return restResult(data, SUCCESS, null);
    }

    public static <T> com.mantulife.common.utils.R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> com.mantulife.common.utils.R<T> error() {
        return restResult(null, FAIL, null);
    }

    public static <T> com.mantulife.common.utils.R<T> error(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> com.mantulife.common.utils.R<T> error(T data) {
        return restResult(data, FAIL, null);
    }

    public static <T> com.mantulife.common.utils.R<T> error(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> com.mantulife.common.utils.R<T> error(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> com.mantulife.common.utils.R<T> restResult(T data, int code, String msg) {
        com.mantulife.common.utils.R<T> apiResult = new com.mantulife.common.utils.R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
