package ru.practicum.explorewithme.client.exceptionHandlers.errorResponse;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String error;
    private List<String> trace;

    public ErrorResponse(HttpStatus status, String error, String trace) {
        this.status = status.value();
        this.error = error;
        trace = trace.replaceAll("\t", "  ");
        String[] tracerows = trace.split(System.lineSeparator());
        this.trace = Arrays.asList(tracerows);
    }
}
