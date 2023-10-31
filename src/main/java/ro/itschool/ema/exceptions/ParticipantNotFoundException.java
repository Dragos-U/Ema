package ro.itschool.ema.exceptions;

public class ParticipantNotFoundException extends RuntimeException{
    public ParticipantNotFoundException(String message){
        super(message);
    }
}
