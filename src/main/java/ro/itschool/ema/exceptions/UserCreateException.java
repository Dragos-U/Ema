package ro.itschool.ema.exceptions;

public class UserCreateException extends RuntimeException {
    public UserCreateException(String message) {
        super(message);
    }
}
