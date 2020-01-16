package priv.theo.commons.exception;

/**
 * @author silence
 * @version 1.0
 * @className BusinessException
 * @date 2018/09/16 上午12:11
 * @description 业务异常
 * @program sdk
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
        super("业务异常");
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
