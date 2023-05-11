package core.core.exception;

/**
 * 自定义异常
 * @author lujiaxin
 * @date 2023/5/11
 */
public class CRRunTimeException extends Exception{

    private ExceptionCouple couple;

    public CRRunTimeException(ExceptionCouple couple) {
        this.couple = couple;
    }

    public CRRunTimeException() {
    }

    public ExceptionCouple getCouple() {
        return couple;
    }

    public void setCouple(ExceptionCouple couple) {
        this.couple = couple;
    }
}
