package ro.itschool.ema.exceptions;

import org.springframework.web.context.request.WebRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEventNotFoundException(EventNotFoundException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventCreateException.class)
    public ResponseEntity<ApiErrorResponse> handleEventCreateException(EventCreateException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ApiErrorResponse> handleEventUpdateException(EventUpdateException ex, WebRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ParticipantCreateException.class)
    public ResponseEntity<ApiErrorResponse> handleParticipantCreateException(ParticipantCreateException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ParticipantNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleParticipantNotFoundException(ParticipantNotFoundException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<ApiErrorResponse> handleUserCreateException(UserCreateException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrganizerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleOrganizerNotFoundException(OrganizerNotFoundException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrganizerCreateException.class)
    public ResponseEntity<ApiErrorResponse> handleOrganizerCreateException(OrganizerCreateException ex, WebRequest request){
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
