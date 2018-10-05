package com.huangsuixin.sdk.exception;

/**
 * @author silence
 * @version 1.0
 * @className ClientException
 * @date 2018/09/16 上午9:32
 * @description 客户端异常
 * @program sdk
 */
public class ClientException extends RuntimeException {
    public ClientException() {
        super("客户端异常");
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

    protected ClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
