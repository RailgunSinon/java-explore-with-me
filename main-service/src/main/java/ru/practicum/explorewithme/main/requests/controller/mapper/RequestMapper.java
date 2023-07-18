package ru.practicum.explorewithme.main.requests.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.main.requests.dto.RequestDto;
import ru.practicum.explorewithme.main.requests.model.Request;

@Component
public class RequestMapper {

    public RequestDto toRequestDto(Request request) {
        return new RequestDto(
            request.getId(),
            request.getCreated(),
            request.getEvent(),
            request.getRequester(),
            request.getStatus()
        );
    }

    public List<RequestDto> toRequestDtoList(List<Request> requests) {
        List<RequestDto> requestDtos = new ArrayList<>();
        for (Request request : requests) {
            requestDtos.add(toRequestDto(request));
        }
        return requestDtos;
    }

    public Request toRequest(RequestDto requestDto) {
        return new Request(
            requestDto.getId(),
            requestDto.getCreated(),
            requestDto.getEvent(),
            requestDto.getRequester(),
            requestDto.getStatus()
        );
    }
}
