package ru.practicum.explorewithme.statserver.exceptionHandlers.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
