package ru.practicum.explorewithme.main.event.controller;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.model.enums.Sort;
import ru.practicum.explorewithme.main.event.service.EventService;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventPublicController {

    private final EventService eventService;

    @GetMapping("/{id}")
    public EventDto getEventByEventId(@PathVariable @Positive Long id,
        HttpServletRequest request) throws NotFoundException {
        log.info("Call EventPublicController getEventByEventId with eventId: {}", id);
        return eventService.getByEventId(id, request);
    }

    @GetMapping
    public List<EventDto> searchEventsPublic(
        @RequestParam(required = false) String text,
        @RequestParam(required = false) String categories,
        @RequestParam(required = false) Boolean paid,
        @RequestParam(required = false) String rangeStart,
        @RequestParam(required = false) String rangeEnd,
        @RequestParam(required = false) Boolean onlyAvailable,
        @RequestParam(required = false) Sort sort,
        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
        @Positive @RequestParam(defaultValue = "10") Integer size,
        HttpServletRequest request) throws InvalidParameterException, IOException {
        log.info("Call EventPublicController searchEventsPublic with text: {}, categories: {}, "
            + "paid: {}, rangeStart: {}, rangeEnd: {}, onlyAvailable: {}, sort: {}, from: {}, "
            + "size: {}", text, paid, rangeStart, rangeEnd, onlyAvailable, sort);
        return eventService
            .searchEventsPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort,
                from, size, request);
    }
}
