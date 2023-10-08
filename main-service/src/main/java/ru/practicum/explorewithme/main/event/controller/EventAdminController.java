package ru.practicum.explorewithme.main.event.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.enums.EventState;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventDtoForAdminUpdate;
import ru.practicum.explorewithme.main.event.service.EventService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class EventAdminController {

    private final EventService eventService;

    @PatchMapping("/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventDto updateEventByAdmin(@PathVariable final Long eventId,
        @RequestBody @Valid EventDtoForAdminUpdate dtoForAdminUpdate) {
        return eventService.updateEventByAdmin(eventId, dtoForAdminUpdate);
    }

    @GetMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public Collection<EventDto> searchEvents(@RequestParam(defaultValue = "") List<Long> users,
        @RequestParam(defaultValue = "") List<String> states,
        @RequestParam(defaultValue = "") List<Long> categories,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeStart,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeEnd,
        @RequestParam(value = "from", required = false, defaultValue = "0")
        @PositiveOrZero(message = "Значение 'from' должно быть положительным") Integer from,
        @RequestParam(value = "size", required = false, defaultValue = "10")
        @Positive(message = "Значение 'size' должно быть положительным") Integer size) {

        return eventService.searchEvents(users,
            states.stream().map(EventState::valueOf).collect(Collectors.toList()),
            categories, rangeStart, rangeEnd, from, size);
    }
}