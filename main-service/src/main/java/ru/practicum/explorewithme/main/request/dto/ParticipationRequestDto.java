package ru.practicum.explorewithme.main.request.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.explorewithme.main.enums.RequestStatus;

@Data
@Builder
public class ParticipationRequestDto {
    String created;
    Long event;
    Long requester;
    Long id;
    RequestStatus status;
}