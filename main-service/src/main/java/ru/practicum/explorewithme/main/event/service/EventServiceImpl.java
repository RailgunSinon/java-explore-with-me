package ru.practicum.explorewithme.main.event.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.StateViewDto;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.category.storage.CategoryStorage;
import ru.practicum.explorewithme.main.event.controller.mapper.EventMapper;
import ru.practicum.explorewithme.main.event.controller.mapper.HitMapper;
import ru.practicum.explorewithme.main.event.controller.mapper.LocationMapper;
import ru.practicum.explorewithme.main.event.dto.EventDto;
import ru.practicum.explorewithme.main.event.dto.EventInputDto;
import ru.practicum.explorewithme.main.event.dto.EventUpdateDto;
import ru.practicum.explorewithme.main.event.dto.LocationDto;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.event.model.Hit;
import ru.practicum.explorewithme.main.event.model.Location;
import ru.practicum.explorewithme.main.event.model.enums.EventState;
import ru.practicum.explorewithme.main.event.model.enums.Sort;
import ru.practicum.explorewithme.main.event.model.enums.StateAction;
import ru.practicum.explorewithme.main.event.storage.EventStorage;
import ru.practicum.explorewithme.main.event.storage.LocationStorage;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.requests.model.ConfirmedRequest;
import ru.practicum.explorewithme.main.requests.storage.RequestStorage;
import ru.practicum.explorewithme.main.user.controller.mapper.UserMapper;
import ru.practicum.explorewithme.main.user.dto.UserInfoDto;
import ru.practicum.explorewithme.main.user.model.User;
import ru.practicum.explorewithme.main.user.storage.UserStorage;
import ru.practicum.explorewithme.main.utility.GeneralUtility;
import ru.practicum.explorewithme.statserver.StatClient;

/*
Вот тут я не очерень уверен, как было лучше. Я перенес классы, что мне нуцжны из статистики -
но это дублицирование. А если вещать зависимость - то более сильная связность кода, одно без другого
не работает. Возникает вопрос - а как правильно?
*/
@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private static final String RANGE_START = "2000-01-01 00:01:01";
    private static final String RANGE_END = "2099-01-01 23:59:59";
    private static final String APP_NAME = "ewm-main-service";
    private static final String URI = "/events/";
    private static final long HOURS_BEFORE_START = 2L;
    private static final long ADMIN_HOURS_BEFORE_START = 1L;
    private static final long MINUTE_LATER_NOW = 1L;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventStorage eventRepository;
    private final LocationStorage locationStorage;
    private final CategoryStorage categoryStorage;
    private final UserStorage userStorage;
    private final RequestStorage requestStorage;
    private final StatClient statClient;
    private final LocationMapper locationMapper;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final HitMapper hitMapper;

    @Override
    @Transactional
    public EventDto addEvent(Long userId, EventInputDto eventNewDto)
        throws ConflictException, NotFoundException, InvalidParameterException {
        log.info("Call EventServiceImpl addEvent with userid: {}, eventNewDto: {}", userId,
            eventNewDto);
        Optional<User> user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found id: " + userId);
        }
        Optional<Category> category = categoryStorage.findById(eventNewDto.getCategory());
        if (category.isEmpty()) {
            throw new NotFoundException("Category dont exists id: " + eventNewDto.getCategory());
        }
        if (LocalDateTime.parse(eventNewDto.getEventDate(), dateTimeFormatter)
            .isAfter(LocalDateTime.now().plusHours(HOURS_BEFORE_START))) {
            Location location = locationStorage.save(eventNewDto.getLocation());
            LocationDto locationDto = locationMapper.toLocationDto(eventNewDto.getLocation());
            Event event = eventRepository
                .save(eventMapper.toEvent(user.get(), location, eventNewDto, category.get()));

            UserInfoDto userDto = userMapper.toUserInfoDto(userStorage.getReferenceById(userId));
            Integer confirmedRequests = requestStorage
                .getAllByEventIdAndConfirmedStatus(event.getId());
            event.setConfirmedRequests(confirmedRequests);
            return eventMapper.toEventDto(event, locationDto, userDto, 0L);
        } else {
            throw new InvalidParameterException("Check event date.");
        }
    }

    @Override
    @Transactional
    public List<EventDto> searchEventsPublic(String text, String categoriesInput, Boolean paid,
        String rangeStart, String rangeEnd, Boolean onlyAvailable, Sort sort, Integer from,
        Integer size, HttpServletRequest request)
        throws IOException, ConflictException, InvalidParameterException {
        log.info("Call EventServiceImpl searchEventsPublic with sort: {}, categories: {}", sort,
            categoriesInput);
        PageRequest pageable = PageRequest.of(from / size, size);
        LocalDateTime startTime;
        LocalDateTime endTime;
        if (rangeStart != null && rangeEnd != null) {
            startTime = LocalDateTime.parse(rangeStart, dateTimeFormatter);
            endTime = LocalDateTime.parse(rangeEnd, dateTimeFormatter);
            if (startTime.isAfter(endTime)) {
                throw new InvalidParameterException(
                    "Start date is after end one!");
            }
        } else {
            startTime = LocalDateTime.now().plusMinutes(MINUTE_LATER_NOW);
            endTime = LocalDateTime.parse(RANGE_END, dateTimeFormatter);
        }
        if (rangeStart != null) {
            startTime = LocalDateTime.parse(rangeStart, dateTimeFormatter);
        }
        if (rangeEnd != null) {
            endTime = LocalDateTime.parse(rangeEnd, dateTimeFormatter);
        }
        List<Event> events = new ArrayList<>();
        List<Long> categories = GeneralUtility.convertToLongFromString(categoriesInput);
        if (sort == null || sort.equals(Sort.VIEWS)) {
            events = eventRepository
                .findEventsByParamsOrderById(text, categories, paid, startTime, endTime,
                    onlyAvailable, pageable);
        } else if (sort != null && sort.equals(Sort.EVENT_DATE)) {
            events = eventRepository
                .findEventsByParamsOrderByDate(text, categories, paid, startTime, endTime,
                    onlyAvailable, pageable);
        } else {
            throw new InvalidParameterException("Wrong sort:" + sort);
        }

        List<Long> eventIds;
        eventIds = events.stream()
            .map(Event::getId)
            .collect(Collectors.toList());

        List<EventDto> eventFullDtoList = new ArrayList<>();
        for (Event event : events) {
            eventFullDtoList.add(
                eventMapper.toEventDtoWhenSearch(event, getEventViewsMap(events, eventIds),
                    getConfirmedRequestsMap(eventIds))
            );
        }

        statClient.hit(hitMapper.toDto(
            new Hit(0, APP_NAME, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now())));
        return eventFullDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto getByEventId(Long eventId, HttpServletRequest request)
        throws NotFoundException {
        log.info("Call EventServiceImpl getByEventId with eventId: {}", eventId);

        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty() || !event.get().getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException("Event not found id: " + eventId);
        }

        String eventUri = URI + event.get().getId();
        List<StateViewDto> viewStatsDtos = statClient
            .stats(RANGE_START, RANGE_END, List.of(eventUri), true).getBody();
        Long views = viewStatsDtos.size() == 0 ? 0 : viewStatsDtos.get(0).getHits();

        event.get().setConfirmedRequests(requestStorage.getConfirmedRequestsByEventId(eventId));
        EventDto eventFullDto = eventMapper.toEventDtoWithView(event.get(), views);
        statClient.hit(hitMapper.toDto(
            new Hit(0, APP_NAME, request.getRequestURI(), request.getRemoteAddr(),
                LocalDateTime.now())));
        return eventFullDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventDto> getByUserId(Long userId, Integer size, Integer from,
        HttpServletRequest request) throws JsonProcessingException {
        log.info("Call EventServiceImpl getByUserId with userid: {}, size: {}, from: {}", userId,
            size, from);
        PageRequest pageable = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.findByInitiatorIdOrderByIdDesc(userId, pageable);
        if (events.size() == 0) {
            return List.of();
        }

        List<Long> eventIds;
        eventIds = events.stream()
            .map(Event::getId)
            .collect(Collectors.toList());

        List<EventDto> eventFullDtoList = new ArrayList<>();
        for (Event event : events) {
            eventFullDtoList.add(
                eventMapper.toEventDtoWhenSearch(event, getEventViewsMap(events, eventIds),
                    getConfirmedRequestsMap(eventIds))
            );
        }
        return eventFullDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public EventDto getByUserAndEventId(Long userId, Long eventId, HttpServletRequest request)
        throws JsonProcessingException, NotFoundException {
        log.info("Call EventServiceImpl getByUserAndEventId with userid: {}, eventId: {}", userId,
            eventId);
        Optional<User> user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found id: " + userId);
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event not found id: " + eventId);
        }
        if (!event.get().getInitiator().getId().equals(userId)) {
            throw new NotFoundException(
                "Event with id: " + eventId + "of user with id: " + userId);
        }

        String eventUri = URI + event.get().getId();
        List<StateViewDto> viewStatsDtos = statClient
            .stats(RANGE_START, RANGE_END, List.of(eventUri), true).getBody();
        Long views = viewStatsDtos.size() == 0 ? 0 : viewStatsDtos.get(0).getHits();

        event.get().setConfirmedRequests(requestStorage.getConfirmedRequestsByEventId(eventId));
        EventDto eventDto = eventMapper.toEventDtoWithView(event.get(), views);
        return eventDto;

    }

    @Override
    @Transactional
    public EventDto updateByUser(Long userId, Long eventId, EventUpdateDto eventUpdateDto,
        HttpServletRequest request)
        throws JsonProcessingException, NotFoundException, ConflictException, InvalidParameterException {
        log.info("Call EventServiceImpl updateByUser with userId: {}, eventId: {}", userId,
            eventId);
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event not found id: " + eventId);
        }
        if (!event.get().getInitiator().getId().equals(userId)) {
            throw new InvalidParameterException("Your are not an event owner");
        }

        if (event.get().getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Rejected - status is PUBLISHED");
        }
        LocalDateTime startTime;
        if (Optional.ofNullable(eventUpdateDto.getEventDate()).isEmpty()) {
            startTime = event.get().getEventDate();
        } else {
            startTime = LocalDateTime.parse(eventUpdateDto.getEventDate(), dateTimeFormatter);
        }
        if (startTime.isBefore(LocalDateTime.now().plusHours(HOURS_BEFORE_START))) {
            throw new InvalidParameterException(" Need at least two hours before start");
        }

        checkAndUpdateEvent(eventUpdateDto, event.get());
        if (Optional.ofNullable(eventUpdateDto.getStateAction()).isPresent()) {
            if (eventUpdateDto.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
                event.get().setState(EventState.PENDING);
            }
            if (eventUpdateDto.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                event.get().setState(EventState.CANCELED);
            }
        }

        Integer confirmedRequest = requestStorage.getConfirmedRequestsByEventId(eventId);
        event.get().setConfirmedRequests(confirmedRequest);

        String eventUri = URI + event.get().getId();
        List<StateViewDto> viewStatsDtos = statClient
            .stats(RANGE_START, RANGE_END, List.of(eventUri), true).getBody();
        Long views = viewStatsDtos.size() == 0 ? 0 : viewStatsDtos.get(0).getHits();
        return eventMapper.toEventDtoWithView(eventRepository.save(event.get()), views);
    }

    @Override
    @Transactional
    public List<EventDto> searchEventsByAdmin(String usersId, String statesString,
        String categoriesString, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer size,
        Integer from, HttpServletRequest request) throws JsonProcessingException {
        log.info(
            "Call EventServiceImpl searchEventsByAdmin with usersId: {}, startTime: {}, endTime: {}",
            usersId, rangeStart, rangeEnd);
        PageRequest pageable = PageRequest.of(from / size, size);
        List<Event> events;

        List<Long> usersIds = GeneralUtility.convertToLongFromString(usersId);
        List<Long> categories = GeneralUtility.convertToLongFromString(categoriesString);
        List<EventState> states = GeneralUtility.convertToListEventStateFromString(statesString);

        events = eventRepository
            .searchEventsByAdmin(usersIds, states, categories, rangeStart, rangeEnd, pageable);

        List<Long> eventIds;
        eventIds = events.stream()
            .map(Event::getId)
            .collect(Collectors.toList());

        List<EventDto> eventFullDtoList = new ArrayList<>();
        for (Event event : events) {
            eventFullDtoList.add(
                eventMapper.toEventDtoWhenSearch(event, getEventViewsMap(events, eventIds),
                    getConfirmedRequestsMap(eventIds))
            );
        }
        return eventFullDtoList;
    }

    @Override
    @Transactional
    public EventDto updateByAdmin(Long eventId, EventUpdateDto eventUpdateDto,
        HttpServletRequest request)
        throws JsonProcessingException, ConflictException, NotFoundException, InvalidParameterException {
        log.info("Call EventServiceImpl updateByAdmin with eventId: {}, eventUpdateDto: {}",
            eventId, eventUpdateDto);
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event not found id: " + event);
        }
        if (event.get().getState().equals(EventState.PUBLISHED) && eventUpdateDto.getStateAction()
            .equals(StateAction.PUBLISH_EVENT)) {
            throw new ConflictException("Event already PUBLISHED.");
        }
        if (event.get().getState().equals(EventState.CANCELED) && eventUpdateDto.getStateAction()
            .equals(StateAction.PUBLISH_EVENT)) {
            throw new ConflictException("Event CANCELED.");
        }
        if (event.get().getState().equals(EventState.PUBLISHED) && eventUpdateDto.getStateAction()
            .equals(StateAction.REJECT_EVENT)) {
            throw new ConflictException("Cant REJECT event with status PUBLISHED.");
        }

        LocalDateTime startTime;
        if (Optional.ofNullable(eventUpdateDto.getEventDate()).isEmpty()) {
            startTime = event.get().getEventDate();
        } else {
            startTime = LocalDateTime.parse(eventUpdateDto.getEventDate(), dateTimeFormatter);
        }
        if (startTime.isAfter(LocalDateTime.now().plusHours(ADMIN_HOURS_BEFORE_START)) && event
            .get().getState().equals(EventState.PENDING)) {
            checkAndUpdateEvent(eventUpdateDto, event.get());
            if (Optional.ofNullable(eventUpdateDto.getStateAction()).isPresent()) {
                if (eventUpdateDto.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                    event.get().setState(EventState.PUBLISHED);
                    event.get().setPublishedOn(LocalDateTime.now());
                }
                if (eventUpdateDto.getStateAction().equals(StateAction.REJECT_EVENT)) {
                    event.get().setState(EventState.CANCELED);
                }
            }
            Integer confirmedRequest = requestStorage.getConfirmedRequestsByEventId(eventId);
            event.get().setConfirmedRequests(confirmedRequest);

            String eventUri = URI + event.get().getId();
            List<StateViewDto> viewStatsDtos = statClient
                .stats(RANGE_START, RANGE_END, List.of(eventUri), true).getBody();

            Long views = viewStatsDtos.size() == 0 ? 0 : viewStatsDtos.get(0).getHits();

            EventDto ed = eventMapper.toEventDtoWithView(eventRepository.save(event.get()), views);
            return ed;
        } else {
            throw new InvalidParameterException("Проверьте статус и время начала события.");
        }
    }

    Map<Long, Long> getConfirmedRequestsMap(List<Long> eventIds) {
        List<ConfirmedRequest> confirmedRequests = requestStorage
            .getConfirmedRequestsByEventIds(eventIds);
        Map<Long, Long> confirmedRequestsMap = new HashMap<>();
        if (confirmedRequests.size() > 0) {
            confirmedRequestsMap = confirmedRequests.stream()
                .collect(Collectors.toMap(
                    cr -> cr.getEventId(),
                    cr -> cr.getCountConfirmedRequest()
                ));
        }
        return confirmedRequestsMap;
    }

    Map<Long, Long> getEventViewsMap(List<Event> events, List<Long> eventIds) {
        List<String> uriEventList = events.stream()
            .map(e -> URI + e.getId().toString())
            .collect(Collectors.toList());
        List<StateViewDto> stateViewDtos = statClient
            .stats(RANGE_START, RANGE_END, uriEventList, true).getBody();
        Map<Long, Long> eventViewsMap = getEventHitsMap(stateViewDtos, eventIds);
        return eventViewsMap;
    }

    private Map<Long, Long> getEventHitsMap(List<StateViewDto> hitDtos, List<Long> eventIds) {
        Map<Long, Long> eventIdHitsMap = new HashMap<>(); // eventId : hits
        if (hitDtos.size() == 0) {
            for (Long eventId : eventIds) {
                eventIdHitsMap.put(eventId, 0L);
            }
            return eventIdHitsMap;
        }
        for (StateViewDto viewStatsDto : hitDtos) {
            eventIdHitsMap.put(Long.parseLong(viewStatsDto.getUri().replace("/events/", "")),
                viewStatsDto.getHits());
        }
        return eventIdHitsMap;
    }

    private void checkAndUpdateEvent(EventUpdateDto eventUpdateDto, Event event)
        throws NotFoundException {
        if (Optional.ofNullable(eventUpdateDto.getTitle()).isPresent()) {
            event.setTitle(eventUpdateDto.getTitle());
        }
        if (Optional.ofNullable(eventUpdateDto.getDescription()).isPresent()) {
            event.setDescription(eventUpdateDto.getDescription());
        }
        if (Optional.ofNullable(eventUpdateDto.getAnnotation()).isPresent()) {
            event.setAnnotation(eventUpdateDto.getAnnotation());
        }
        if (Optional.ofNullable(eventUpdateDto.getCategory()).isPresent()) {
            Optional<Category> category = categoryStorage.findById(eventUpdateDto.getCategory());
            if (category.isEmpty()) {
                throw new NotFoundException(
                    "Category does not exists id: " + eventUpdateDto.getCategory());
            }
            event.setCategory(category.get());
        }
        if (Optional.ofNullable(eventUpdateDto.getEventDate()).isPresent()) {
            LocalDateTime eventDate = LocalDateTime
                .parse(eventUpdateDto.getEventDate(), dateTimeFormatter);
            event.setEventDate(eventDate);
        }
        if (Optional.ofNullable(eventUpdateDto.getLocation()).isPresent()) {
            event.setLocation(locationStorage.save(eventUpdateDto.getLocation()));
        }
        if (Optional.ofNullable(eventUpdateDto.getPaid()).isPresent()) {
            event.setPaid(Boolean.parseBoolean(eventUpdateDto.getPaid()));
        }
        if (Optional.ofNullable(eventUpdateDto.getParticipantLimit()).isPresent()) {
            event.setParticipantLimit(eventUpdateDto.getParticipantLimit());
        }
        if (Optional.ofNullable(eventUpdateDto.getRequestModeration()).isPresent()) {
            event.setRequestModeration(Boolean.parseBoolean(eventUpdateDto.getRequestModeration()));
        }
    }
}
