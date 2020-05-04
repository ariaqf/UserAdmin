package user.business.service.exceptions;

public class InvalidCredentialException extends Exception {
    public InvalidCredentialException() {
    }

    public InvalidCredentialException(String message) {
        super(message);
    }
}
