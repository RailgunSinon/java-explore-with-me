package ru.practicum.explorewithme.statserver.exceptionHandlers.errorResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String error;
    private final String status;
}