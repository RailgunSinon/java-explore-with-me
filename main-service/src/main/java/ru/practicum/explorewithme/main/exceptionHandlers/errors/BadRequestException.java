package ru.practicum.explorewithme.main.exceptionHandlers.errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}