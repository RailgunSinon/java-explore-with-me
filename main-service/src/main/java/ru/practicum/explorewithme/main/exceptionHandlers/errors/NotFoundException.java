package ru.practicum.explorewithme.main.exceptionHandlers.errors;

public class NotFoundException extends  RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}