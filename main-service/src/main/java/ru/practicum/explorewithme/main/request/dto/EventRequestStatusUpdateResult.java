package ru.practicum.explorewithme.main.request.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}