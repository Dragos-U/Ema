package ro.itschool.ema.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private final ObjectMapper objectMapper;

    public ExceptionHandlerAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(ParticipantCreateException.class)
    public ResponseEntity<String> participantCreateException(ParticipantCreateException participantCreateException) {
        return new ResponseEntity<>(objectToString(Map.of("message: ", participantCreateException.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventCreateException.class)
    public ResponseEntity<String> eventCreateException(EventCreateException eventCreateException) {
        return new ResponseEntity<>(objectToString(Map.of("message: ", eventCreateException.getMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserCreateException.class)
    public ResponseEntity<String> userCreateException(UserCreateException userCreateException) {
        return new ResponseEntity<>(objectToString(Map.of("message: ", userCreateException.getMessage())), HttpStatus.BAD_REQUEST);
    }

    private String objectToString(Object response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("Error processing response to string");
            return "Internal error";
        }
    }

}
