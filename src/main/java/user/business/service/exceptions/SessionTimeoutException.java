package user.business.service.exceptions;

public class SessionTimeoutException extends Exception {
    public SessionTimeoutException() {
    }

    public SessionTimeoutException(String message) {
        super(message);
    }
}
