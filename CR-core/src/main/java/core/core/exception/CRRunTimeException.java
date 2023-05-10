package core.core.exception;

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
