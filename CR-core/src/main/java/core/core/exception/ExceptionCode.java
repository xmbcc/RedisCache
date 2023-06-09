package core.core.exception;

/**
 * 异常编码
 * 便于统一管理
 * @author lujiaxin
 * @date 2023/5/11
 */
public class ExceptionCode {

    public static ExceptionCouple KEY_FORMAT_ERROR = new ExceptionCouple("输入Key格式错误！",101);
    public static ExceptionCouple KEY_NOTFIND_ERROR = new ExceptionCouple("Key不存在！",102);
    public static ExceptionCouple VALUE_FORMAT_ERROR = new ExceptionCouple("Value格式错误！！",103);
    public static ExceptionCouple KEY_VALUE_FORMAT_ERROR = new ExceptionCouple("Key或Value格式错误！！",104);
    public static ExceptionCouple LFU_MIN_NOTFIND = new ExceptionCouple("未找到使用频次最小的！",105);

}
