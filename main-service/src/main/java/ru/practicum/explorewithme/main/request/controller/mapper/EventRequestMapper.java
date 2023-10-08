package ru.practicum.explorewithme.main.request.controller.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;
import ru.practicum.explorewithme.main.enums.RequestStatus;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.main.request.model.EventRequest;
import ru.practicum.explorewithme.main.user.model.User;

@UtilityClass
public class EventRequestMapper {

    public static EventRequest makeEventRequest(Event event, User requester) {
        return EventRequest.builder()
            .created(LocalDateTime.now())
            .event(event)
            .requester(requester)
            .status(event.getRequestModeration() ? RequestStatus.PENDING : RequestStatus.CONFIRMED)
            .build();
    }

    public static ParticipationRequestDto toRequestDto(EventRequest request) {
        return ParticipationRequestDto.builder()
            .created(
                request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .event(request.getEvent().getId())
            .id(request.getId())
            .requester(request.getRequester().getId())
            .status(request.getStatus())
            .build();
    }
}