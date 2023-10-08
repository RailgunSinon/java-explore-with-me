package ru.practicum.explorewithme.main.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ru.practicum.explorewithme.main.enums.EventState;
import ru.practicum.explorewithme.main.enums.RequestStatus;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.event.repository.EventRepository;
import ru.practicum.explorewithme.main.exceptionHandlers.errors.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.errors.NotFoundException;
import ru.practicum.explorewithme.main.request.controller.mapper.EventRequestMapper;
import ru.practicum.explorewithme.main.request.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.main.request.model.EventRequest;
import ru.practicum.explorewithme.main.request.repository.EventRequestRepository;
import ru.practicum.explorewithme.main.user.model.User;
import ru.practicum.explorewithme.main.user.repository.UserRepository;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventRequestRepository requestRepository;

    @Override
    @Transactional
    public ParticipationRequestDto addNewRequest(Long userId, Long eventId) {
        User requester = getRequester(userId);
        Event event = getEvent(eventId);
        EventRequest request = EventRequestMapper.makeEventRequest(event, requester);

        if (requestRepository.findAllByRequesterIdAndEventId(userId, eventId).size() != 0) {
            throw new ConflictException("Вы уже добавляли запрос на участие в этом событие!");
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException("Вы являетесь инициатором события и не можете добавить запрос на участие!");
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Данное событие еще не опубликовано!");
        }
        if (event.getParticipantLimit() != 0) {
            List<EventRequest> eventRequests = requestRepository.findAllByEventId(eventId);
            if (event.getParticipantLimit() <= eventRequests.size()) {
                throw new ConflictException("Достигнут лимит запросов на участие в этом событии!");
            }
        } else {
            request.setStatus(RequestStatus.CONFIRMED);
        }

        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatus.CONFIRMED);
        }
        log.info("Пользователь с id = {} добавил запрос на участие в событии с id = {}", userId, eventId);
        return EventRequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        User requester = getRequester(userId);
        EventRequest request = getRequest(requestId);
        if (!requester.getId().equals(request.getRequester().getId())) {
            throw new ConflictException("Вы не можете отменить чужой запрос!");
        }
        request.setStatus(RequestStatus.CANCELED);
        log.info("Пользователь с id = {} отменил свой запрос с id = {}", userId, requestId);
        return EventRequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public Collection<ParticipationRequestDto> getAllUserRequests(Long userId) {
        User requester = getRequester(userId);
        List<EventRequest> userRequests = requestRepository.findAllByRequester(requester);
        log.info("Получен список всех запросов пользователя с id = {}", userId);
        return userRequests.stream().map(EventRequestMapper::toRequestDto).collect(Collectors.toList());
    }

    private User getRequester(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
            new NotFoundException(String.format("Пользователь с id=%d не найден", userId)));
    }

    private Event getEvent(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
            new NotFoundException(String.format("Событие с id=%d не найдено", eventId)));
    }

    private EventRequest getRequest(Long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
            new NotFoundException(String.format("Запрос с id=%d не найден", requestId)));
    }
}