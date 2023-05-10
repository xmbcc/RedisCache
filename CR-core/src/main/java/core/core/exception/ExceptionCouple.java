package core.core.exception;

/**
 * 异常信息封装
 * @author lujiaxin
 * @date 2023/5/10
 */
public class ExceptionCouple {

    private String message;
    private Integer code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ExceptionCouple() {
    }

    public ExceptionCouple(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
