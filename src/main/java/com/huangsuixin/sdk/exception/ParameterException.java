package com.huangsuixin.sdk.exception;

/**
 * @author silence
 * @version 1.0
 * @className ParameterException
 * @date 2018/09/16 上午12:10
 * @description 参数异常
 * @program sdk
 */
public class ParameterException extends RuntimeException {
    public ParameterException() {
        super("参数异常");
    }

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterException(Throwable cause) {
        super(cause);
    }

    protected ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
