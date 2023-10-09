package ru.practicum.explorewithme.main.event.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import ru.practicum.explorewithme.main.enums.EventState;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventDtoForAdminUpdate;
import ru.practicum.explorewithme.main.event.dto.EventDtoForUserUpdate;
import ru.practicum.explorewithme.main.event.dto.EventShortDto;
import ru.practicum.explorewithme.main.event.dto.NewEventDto;
import ru.practicum.explorewithme.main.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.main.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.main.request.dto.ParticipationRequestDto;

public interface EventService {

    EventDto addNewEvent(NewEventDto newEventDto, Long userId);

    Collection<EventShortDto> getAllByUserId(Long userId, Integer from, Integer size);

    EventDto getEventByInitiatorIdAndEventId(Long initiatorId, Long eventId);

    EventDto updateEventByInitiator(Long initiatorId, Long eventId,
        EventDtoForUserUpdate eventDtoForUserUpdate);

    EventDto updateEventByAdmin(Long eventId, EventDtoForAdminUpdate eventDtoForAdminUpdate);

    Collection<EventDto> searchEvents(List<Long> users, List<EventState> states,
        List<Long> categories,
        LocalDateTime rangeStart, LocalDateTime rangeEnd,
        Integer from, Integer size);

    Collection<ParticipationRequestDto> getRequestsByUserAndEvent(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId,
        EventRequestStatusUpdateRequest statusUpdateRequest);

    EventDto getPublicEventById(Long eventId, String url, String ip);

    Collection<EventDto> getPublicEventsByFilters(String text, List<Long> categories, Boolean paid,
        LocalDateTime rangeStart, LocalDateTime rangeEnd,
        Boolean onlyAvailable, String sort,
        Integer from, Integer size, String url, String ip);
}