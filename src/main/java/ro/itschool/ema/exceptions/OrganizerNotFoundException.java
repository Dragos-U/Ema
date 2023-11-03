package ro.itschool.ema.exceptions;

public class OrganizerNotFoundException extends RuntimeException {
    public OrganizerNotFoundException(String message) {
        super(message);
    }
}
