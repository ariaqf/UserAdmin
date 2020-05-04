package user.business.service.exceptions;

public class WrongLoginParametersException extends Exception {
    public WrongLoginParametersException() {
        super();
    }

    public WrongLoginParametersException(String msg) {
        super(msg);
    }
}
