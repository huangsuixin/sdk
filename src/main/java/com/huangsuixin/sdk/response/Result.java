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

    private String msg;

    private T data;

    private Integer code;


    public Result() {
    }

    @Deprecated
    public Result(boolean success) {
        this(success, null, null);
    }

    @Deprecated
    public Result(boolean success, String msg) {
        this(success, msg, null);
    }

    public Result(boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> buildSuccessResult(T data) {
        return new Result<T>().setSuccess(true).setMsg(ResultEnum.SUCCESS.msg()).setData(data).setCode(ResultEnum.SUCCESS.code());
    }

    public static <T> Result<T> buildSuccessResult(String message, T data) {
        return new Result<T>().setSuccess(true).setMsg(message).setData(data).setCode(ResultEnum.SUCCESS.code());
    }

    public static Result buildEmptySuccessResult() {
        return new Result().setSuccess(true).setCode(ResultEnum.SUCCESS.code()).setMsg(ResultEnum.SUCCESS.msg());
    }

    public static Result buildEmptyFailureResult() {
        return new Result().setSuccess(false).setCode(ResultEnum.FAILTURE.code()).setMsg(ResultEnum.FAILTURE.msg());
    }

    public static <TData> Result<TData> buildFailureResult(int code, String message) {
        return new Result<TData>().setSuccess(false).setCode(code).setMsg(message);
    }



    public boolean isSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Result<T> setCode(Integer code) {
        this.code = code;
        return this;
    }
}

