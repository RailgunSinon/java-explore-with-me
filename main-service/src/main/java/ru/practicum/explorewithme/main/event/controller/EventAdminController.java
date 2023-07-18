package ru.practicum.explorewithme.main.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventUpdateDto;
import ru.practicum.explorewithme.main.event.service.EventService;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

/*
Выделил тут патерн даты, а то в строку не лезет
*/
@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
public class EventAdminController {

    private final EventService eventService;
    private final String pattern = "yyyy-MM-dd HH:mm:ss";

    @PatchMapping("/{eventId}")
    public EventDto updateEventByAdmin(@PathVariable @Positive Long eventId,
        @Valid @RequestBody EventUpdateDto eventUpdateDto, HttpServletRequest request)
        throws NotFoundException, InvalidParameterException, ConflictException, JsonProcessingException {
        log.info("Call EventAdminController updateEventByAdmin with eventId: {}, "
            + "eventUpdateDto: {}", eventId, eventUpdateDto);
        return eventService.updateByAdmin(eventId, eventUpdateDto, request);
    }

    @GetMapping
    public List<EventDto> searchEventsByAdmin(
        @RequestParam(required = false) String users,
        @RequestParam(required = false) String states,
        @RequestParam(required = false) String categories,
        @RequestParam(required = false) @DateTimeFormat(pattern = pattern) LocalDateTime rangeStart,
        @RequestParam(required = false) @DateTimeFormat(pattern = pattern) LocalDateTime rangeEnd,
        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
        @Positive @RequestParam(defaultValue = "10") Integer size,
        HttpServletRequest request) throws JsonProcessingException {
        log.info("Call EventAdminController searchEventsByAdmin with size {} from {} ", size, from);
        return eventService
            .searchEventsByAdmin(users, states, categories, rangeStart, rangeEnd, size, from,
                request);
    }

}
