package ru.practicum.explorewithme.main.request.service;


import java.util.Collection;
import ru.practicum.explorewithme.main.request.dto.ParticipationRequestDto;

public interface EventRequestService {

    ParticipationRequestDto addNewRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);

    Collection<ParticipationRequestDto> getAllUserRequests(Long userId);
}