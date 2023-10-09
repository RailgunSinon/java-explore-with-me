package ru.practicum.explorewithme.main.request.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.main.enums.RequestStatus;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    String created;
    Long event;
    Long requester;
    Long id;
    RequestStatus status;
}