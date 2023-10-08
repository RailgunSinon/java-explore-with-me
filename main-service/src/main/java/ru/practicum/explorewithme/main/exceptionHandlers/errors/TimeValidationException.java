package ru.practicum.explorewithme.main.exceptionHandlers.errors;

public class TimeValidationException extends RuntimeException {
    public TimeValidationException(String message) {
        super(message);
    }
}