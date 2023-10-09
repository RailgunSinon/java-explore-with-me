package ru.practicum.explorewithme.main.exceptionHandlers.errorResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ErrorResponse {
    String error;
    String status;
}
