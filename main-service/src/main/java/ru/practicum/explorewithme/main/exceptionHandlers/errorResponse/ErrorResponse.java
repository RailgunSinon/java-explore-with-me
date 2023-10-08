package ru.practicum.explorewithme.main.exceptionHandlers.errorResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ErrorResponse {
    private final String error;
    private final String status;
}
