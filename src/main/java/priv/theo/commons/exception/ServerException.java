package priv.theo.commons.exception;

/**
 * @author silence
 * @version 1.0
 * @className ServerException
 * @date 2018/09/16 上午12:09
 * @description 服务端异常
 * @program sdk
 */
public class ServerException extends RuntimeException {
    public ServerException() {
        super("服务端异常");
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    protected ServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
