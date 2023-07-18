package ru.practicum.explorewithme.main.exceptionHandlers;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.main.exceptionHandlers.errorResponse.ErrorMessage;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(NotFoundException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setReason("Object Not Found");
        errorMessage.setStatus("NOT_FOUND");
        errorMessage.setTimestamp(LocalDateTime.now().format(formatter));
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleException(ConflictException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setReason("Conflict detected");
        errorMessage.setStatus("CONFLICT");
        errorMessage.setTimestamp(LocalDateTime.now().format(formatter));
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorMessage> handleException(InvalidParameterException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setReason("Bad request");
        errorMessage.setStatus("BAD_REQUEST");
        errorMessage.setTimestamp(LocalDateTime.now().format(formatter));
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleException(RuntimeException e) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setMessage(e.getMessage());
        errorMessage.setReason("Unknown Exception");
        errorMessage.setStatus("INTERNAL_SERVER_ERROR");
        errorMessage.setTimestamp(LocalDateTime.now().format(formatter));
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
