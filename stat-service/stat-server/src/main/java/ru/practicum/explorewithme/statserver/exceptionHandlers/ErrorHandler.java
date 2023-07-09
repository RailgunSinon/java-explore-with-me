package ru.practicum.explorewithme.statserver.exceptionHandlers;

import java.io.PrintWriter;
import java.io.StringWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.statserver.exceptionHandlers.errorResponse.ErrorResponse;

@RestControllerAdvice("ru.practicum.stats")
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse defaultError(final Throwable e) {
        log.error("{} {}", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        String stackTrace = out.toString();
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), stackTrace);
    }
}
