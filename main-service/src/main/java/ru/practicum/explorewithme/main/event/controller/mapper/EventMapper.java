package ru.practicum.explorewithme.main.event.controller.mapper;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import ru.practicum.explorewithme.main.category.controller.mapper.CategoryMapper;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.enums.EventState;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventShortDto;
import ru.practicum.explorewithme.main.event.dto.NewEventDto;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.user.controller.mapper.UserMapper;
import ru.practicum.explorewithme.main.user.model.User;

@UtilityClass
public class EventMapper {
    public static Event toEvent(NewEventDto newEventDto, Category category, User initiator) {
        return Event.builder()
            .annotation(newEventDto.getAnnotation())
            .category(category)
            .confirmedRequests(0L)
            .createdOn(LocalDateTime.now())
            .description(newEventDto.getDescription())
            .eventDate(newEventDto.getEventDate())
            .initiator(initiator)
            .location(newEventDto.getLocation() != null ? LocationMapper.toLocation(newEventDto.getLocation()) : null)
            .paid(newEventDto.getPaid() != null ? newEventDto.getPaid() : false)
            .participantLimit(newEventDto.getParticipantLimit() != null ? newEventDto.getParticipantLimit() : 0L)
            .publishedOn(LocalDateTime.now())
            .requestModeration(newEventDto.getRequestModeration() != null ? newEventDto.getRequestModeration() : true)
            .state(EventState.PENDING)
            .title(newEventDto.getTitle())
            .views(0L)
            .build();
    }

    public static EventDto toEventFullDto(Event event) {
        return EventDto.builder()
            .annotation(event.getAnnotation())
            .category(CategoryMapper.toCategoryDto(event.getCategory()))
            .confirmedRequests(event.getConfirmedRequests() != null ? event.getConfirmedRequests() : 0)
            .createdOn(event.getCreatedOn())
            .description(event.getDescription())
            .eventDate(event.getEventDate())
            .id(event.getId())
            .initiator(UserMapper.toUserShortDto(event.getInitiator()))
            .location(LocationMapper.toLocationDto(event.getLocation()))
            .paid(event.getPaid())
            .participantLimit(event.getParticipantLimit())
            .publishedOn(event.getPublishedOn())
            .requestModeration(event.getRequestModeration())
            .state(event.getState())
            .title(event.getTitle())
            .views(event.getViews() != null ? event.getViews() : 0)
            .comments(event.getComments() != null ? event.getComments() : 0)
            .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
            .annotation(event.getAnnotation())
            .category(CategoryMapper.toCategoryDto(event.getCategory()))
            .confirmedRequests(event.getConfirmedRequests() != null ? event.getConfirmedRequests() : null)
            .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
            .id(event.getId())
            .initiator(UserMapper.toUserShortDto(event.getInitiator()))
            .paid(event.getPaid())
            .title(event.getTitle())
            .views(event.getViews())
            .comments(event.getComments() != null ? event.getComments() : 0)
            .build();
    }

    public List<EventDto> toEventDtoListWithViews(List<Event> events, Map<String, Long> eventViews) {
        return events.stream().map(event -> {
            eventViews.get(String.format("/events/%s", event.getId()));
            return toEventFullDto(event);
        }).collect(Collectors.toList());
    }

    public Set<EventShortDto> toEventDtoSet(Set<Event> events) {
        return events.stream().map(EventMapper::toEventShortDto).collect(Collectors.toSet());
    }
}