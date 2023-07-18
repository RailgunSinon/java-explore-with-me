package ru.practicum.explorewithme.main.event.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventInputDto;
import ru.practicum.explorewithme.main.event.dto.EventUpdateDto;
import ru.practicum.explorewithme.main.event.model.enums.Sort;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

public interface EventService {

    EventDto addEvent(Long id, EventInputDto eventNewDto)
        throws ConflictException, NotFoundException, InvalidParameterException;

    List<EventDto> searchEventsPublic(String text,
        String categories,
        Boolean paid,
        String rangeStart,
        String rangeEnd,
        Boolean onlyAvailable,
        Sort sort,
        Integer from,
        Integer size,
        HttpServletRequest request)
        throws IOException, ConflictException, InvalidParameterException;

    EventDto getByEventId(Long id, HttpServletRequest request) throws NotFoundException;

    List<EventDto> getByUserId(Long id, Integer size, Integer from, HttpServletRequest request)
        throws JsonProcessingException;

    EventDto getByUserAndEventId(Long userId, Long eventId, HttpServletRequest request)
        throws JsonProcessingException, NotFoundException;

    EventDto updateByUser(Long userId, Long eventId, EventUpdateDto eventUpdateDto,
        HttpServletRequest request)
        throws JsonProcessingException, NotFoundException, ConflictException, InvalidParameterException;

    List<EventDto> searchEventsByAdmin(String usersId, String states, String categories,
        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer size, Integer from,
        HttpServletRequest request) throws JsonProcessingException;

    EventDto updateByAdmin(Long eventId, EventUpdateDto eventUpdateDto, HttpServletRequest request)
        throws JsonProcessingException, ConflictException, NotFoundException, InvalidParameterException;
}
