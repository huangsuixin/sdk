package priv.theo.commons.response;

/**
 * @author silence
 * @version 1.0
 * @className ResultEnum
 * @date 2018/09/16 上午9:48
 * @description 响应码
 * @program sdk
 */
public enum ResultEnum {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    FAILTURE(-1 ,"失败"),

    /**
     * 未知错误
     */
    UNKNOWN_ERROR(1, "未知错误"),
    /**
     * 服务暂不可用
     */
    SERVICE_TEMPORARILY_UNAVAILABLE(2, "服务暂不可用"),
    /**
     * 未知的方法
     */
    UNSUPPORTED_OPENAPI_METHOD(3, "未知的方法"),

    /**
     * 请求来自未经授权的IP地址
     */
    UNAUTHORIZED_CLIENT_IP_ADDRESS(5, "请求来自未经授权的IP地址"),
    /**
     * 无权限访问该用户数据
     */
    NO_PERMISSION_TO_ACCESS_DATA(6, "无权限访问该用户数据"),
    /**
     * 来自该refer的请求无访问权限
     */
    NO_PERMISSION_TO_ACCESS_DATA_FOR_THIS_REFERER(7, "来自该refer的请求无访问权限"),
    /**
     * 请求参数无效
     */
    INVALID_PARAMETER(100, "请求参数无效"),
    /**
     * api key无效
     */
    INVALID_API_KEY(101, "api key无效"),
    /**
     * session key无效
     */
    SESSION_KEY_INVALID_OR_NO_LONGER_VALID(102, "session key无效"),
    /**
     * 无效签名
     */
    INCORRECT_SIGNATURE(104, "无效签名"),
    /**
     * 用户不可见
     */
    USER_NOT_VISIBLE(210, "用户不可见"),
    /**
     * 获取未授权的字段
     */
    UNSUPPORTED_PERMISSION(211, "获取未授权的字段"),
    /**
     * 未知的存储操作错误
     */
    UNKNOWN_DATA_STORE_API_ERROR(800, "未知的存储操作错误"),
    /**
     * 无效的操作方法
     */
    INVALID_OPERATION(801, "无效的操作方法"),
    /**
     * 指定的对象不存在
     */
    SPECIFIED_OBJECT_CANNOT_BE_FOUND(803, "指定的对象不存在"),
    /**
     * 数据库操作出错，请重试
     */
    A_DATABASE_ERROR_OCCURRED_PLEASE_TRY_AGAIN(805, "数据库操作出错，请重试"),
    /**
     * 访问的应用不存在
     */
    NO_SUCH_APPLICATION_EXISTS(900, "访问的应用不存在"),
    ;

    private int code;

    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
