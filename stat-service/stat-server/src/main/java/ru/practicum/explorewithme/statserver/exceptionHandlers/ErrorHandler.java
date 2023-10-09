package ru.practicum.explorewithme.statserver.exceptionHandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.statserver.exceptionHandlers.errorResponse.ErrorResponse;
import ru.practicum.explorewithme.statserver.exceptionHandlers.errors.BadRequestException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(final BadRequestException e) {
        log.info("Bad request error: {}", e.getMessage());
        return new ErrorResponse("Ошибка валидации данных: " + e.getMessage(),
            HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}
