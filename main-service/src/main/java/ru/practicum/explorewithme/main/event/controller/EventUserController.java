package ru.practicum.explorewithme.main.event.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.InvalidParameterException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventInputDto;
import ru.practicum.explorewithme.main.event.dto.EventUpdateDto;
import ru.practicum.explorewithme.main.event.service.EventService;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
public class EventUserController {

    private final EventService eventService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventDto addEvent(@PathVariable @Positive Long userId,
        @RequestBody @Valid EventInputDto eventDto)
        throws ConflictException, NotFoundException, InvalidParameterException {
        log.info("Call EventUserController addEvent with userId: {}, eventNewDto: {}", userId,
            eventDto);
        return eventService.addEvent(userId, eventDto);
    }

    @PatchMapping("/{eventId}")
    public EventDto updateEventByUser(@PathVariable Long userId,
        @PathVariable @Positive Long eventId,
        @Valid @RequestBody EventUpdateDto eventUpdateDto,
        HttpServletRequest request)
        throws JsonProcessingException, NotFoundException, ConflictException, InvalidParameterException {
        log.info("Call EventUserController updateEventByUser with userId: {},eventId: {}, "
            + "eventNewDto: {}", userId, eventId, eventUpdateDto);
        return eventService.updateByUser(userId, eventId, eventUpdateDto, request);
    }

    @GetMapping
    public List<EventDto> getEventsByUserId(@PathVariable Long userId,
        @RequestParam(defaultValue = "10") @Positive Integer size,
        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
        HttpServletRequest request) throws JsonProcessingException {
        log.info("Call EventUserController getEventsByUserId with userId: {}, size: {}, "
            + "from: {}", userId, size, from);
        return eventService.getByUserId(userId, size, from, request);
    }

    @GetMapping("/{eventId}")
    public EventDto getEventByUserAndEventId(@PathVariable Long userId,
        @PathVariable @Positive Long eventId,
        HttpServletRequest request) throws JsonProcessingException, NotFoundException {
        log.info("Call EventUserController getEventByUserAndEventId with eventId: {}, userId: {}, "
            + "size: {}, from: {}", eventId, userId);
        return eventService.getByUserAndEventId(userId, eventId, request);
    }

}
