package ru.practicum.explorewithme.main.exceptionHandlers.errors;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}