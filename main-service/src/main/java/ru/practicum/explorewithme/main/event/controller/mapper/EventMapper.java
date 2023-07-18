package ru.practicum.explorewithme.main.event.controller.mapper;

import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.main.category.controller.mapper.CategoryMapper;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventInfoWithConfirmedDto;
import ru.practicum.explorewithme.main.event.dto.EventInputDto;
import ru.practicum.explorewithme.main.event.dto.LocationDto;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.event.model.Location;
import ru.practicum.explorewithme.main.event.model.enums.EventState;
import ru.practicum.explorewithme.main.user.controller.mapper.UserMapper;
import ru.practicum.explorewithme.main.user.dto.UserInfoDto;
import ru.practicum.explorewithme.main.user.model.User;

@Component
@RequiredArgsConstructor
public class EventMapper {

    CategoryMapper categoryMapper;
    UserMapper userMapper;
    LocationMapper locationMapper;

    public EventDto toEventDtoWhenSearch(Event event,
        Map<Long, Long> eventViewsMap,
        Map<Long, Long> confirmedRequestsMap) {
        return new EventDto(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getAnnotation(),
            event.getState(),
            event.getCategory(),
            event.getCreatedOn(),
            event.getEventDate(),
            event.getPublishedOn(),
            confirmedRequestsMap.containsKey(event.getId()) ? confirmedRequestsMap
                .get(event.getId()).intValue() : 0,
            locationMapper.toLocationDto(event.getLocation()),
            userMapper.toUserInfoDto(event.getInitiator()),
            event.isPaid(),
            event.getParticipantLimit(),
            event.isRequestModeration(),
            eventViewsMap.get(event.getId())
        );
    }

    public EventDto toEventDtoWithView(Event event, Long views) {
        return new EventDto(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getAnnotation(),
            event.getState(),
            event.getCategory(),
            event.getCreatedOn(),
            event.getEventDate(),
            event.getPublishedOn(),
            event.getConfirmedRequests(),
            locationMapper.toLocationDto(event.getLocation()),
            userMapper.toUserInfoDto(event.getInitiator()),
            event.isPaid(),
            event.getParticipantLimit(),
            event.isRequestModeration(),
            views
        );
    }

    public EventInfoWithConfirmedDto toEventDtoInfo(
        Event event) {
        return new EventInfoWithConfirmedDto(
            event.getId(),
            event.getAnnotation(),
            categoryMapper.toCategoryDto(event.getCategory()),
            event.getConfirmedRequests(),
            event.getEventDate().toString(),
            userMapper.toUserInfoDto(event.getInitiator()),
            event.isPaid(),
            event.getTitle(),
            0L
        );
    }

    public EventDto toEventDto(
        Event event, LocationDto locationDto, UserInfoDto userShortDto, Long views) {
        return new EventDto(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getAnnotation(),
            event.getState(),
            event.getCategory(),
            event.getCreatedOn(),
            event.getEventDate(),
            event.getPublishedOn(),
            event.getConfirmedRequests(),
            locationDto,
            userShortDto,
            event.isPaid(),
            event.getParticipantLimit(),
            event.isRequestModeration(),
            views
        );
    }

    public Event toEvent(User user, Location location, EventInputDto eventNewDto,
        Category category) {
        return new Event(
            eventNewDto.getId(),
            eventNewDto.getTitle(),
            eventNewDto.getDescription(),
            eventNewDto.getAnnotation(),
            EventState.valueOf("PENDING"),
            category,
            LocalDateTime.now(),
            LocalDateTime.parse(eventNewDto.getEventDate().replaceAll(" ", "T")),
            null,
            0,
            location,
            user,
            eventNewDto.isPaid(),
            eventNewDto.getParticipantLimit() == null ? 0 : eventNewDto.getParticipantLimit(),
            eventNewDto.getRequestModeration() == null || eventNewDto.getRequestModeration()
        );
    }
}
