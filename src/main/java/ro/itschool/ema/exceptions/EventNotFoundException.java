package ro.itschool.ema.exceptions;

public class EventNotFoundException extends RuntimeException{

    public EventNotFoundException(String message) {
        super(message);
    }
}
