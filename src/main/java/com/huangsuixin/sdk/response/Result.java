package com.huangsuixin.sdk.response;

/**
 * @author suixin
 * @version 1.0
 * @className Result
 * @date 2018/8/2 10:14
 * @description 相应结果封装
 * @program codecount
 */
public class Result<T> {
    private boolean success;

    private String message;

    private T data;

    private Integer code;


    public Result() {
    }

    public Result(boolean success) {
        this(success, null, null);
    }

    public Result(boolean success, String message) {
        this(success, message, null);
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}

