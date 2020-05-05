package user.business.service.exceptions;

public class InvalidDataException extends Throwable {
    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }
}
