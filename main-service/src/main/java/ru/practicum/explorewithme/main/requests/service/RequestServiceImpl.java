package ru.practicum.explorewithme.main.requests.service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.event.model.enums.EventState;
import ru.practicum.explorewithme.main.event.storage.EventStorage;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.requests.controller.mapper.RequestMapper;
import ru.practicum.explorewithme.main.requests.dto.RequestDto;
import ru.practicum.explorewithme.main.requests.dto.RequestUpdateDto;
import ru.practicum.explorewithme.main.requests.dto.RequestUpdateResultDto;
import ru.practicum.explorewithme.main.requests.model.Request;
import ru.practicum.explorewithme.main.requests.model.enums.RequestState;
import ru.practicum.explorewithme.main.requests.model.enums.RequestUpdateState;
import ru.practicum.explorewithme.main.requests.storage.RequestStorage;
import ru.practicum.explorewithme.main.user.model.User;
import ru.practicum.explorewithme.main.user.storage.UserStorage;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestStorage requestStorage;
    private final EventStorage eventStorage;
    private final UserStorage userStorage;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public Request createRequest(Long userId, Long eventId)
        throws ConflictException, NotFoundException {
        log.info("Call RequestServiceImpl createRequest with userId: {}, eventId: {}", userId,
            eventId);
        Optional<User> user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found Id: " + userId);
        }
        Optional<Event> event = eventStorage.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event not found id: {}" + eventId);
        }
        Optional<Request> request = requestStorage.findByEventIdAndRequesterId(eventId, userId);
        if (request.isPresent()) {
            throw new ConflictException("Вы уже делали запрос на это событие");
        }

        if (event.get().getState() != EventState.PUBLISHED) {
            throw new ConflictException("Событие на опубликовано. Запрос отклонен");
        }
        if (Objects.equals(event.get().getInitiator().getId(), userId)) {
            throw new ConflictException("Вы не можете делать запрос на собственное событие");
        }

        Integer confirmedRequestCount = requestStorage.getConfirmedRequestsByEventId(eventId);
        if (confirmedRequestCount == null) {
            confirmedRequestCount = 0;
        }

        if (event.get().getParticipantLimit() != 0
            && event.get().getParticipantLimit() <= confirmedRequestCount) {
            throw new ConflictException("Достигнут лимит участников");
        }
        RequestState status = RequestState.PENDING;
        if (!event.get().isRequestModeration() || event.get().getParticipantLimit() == 0) {
            status = RequestState.CONFIRMED;
        }
        Request newRequest = new Request(null,
            LocalDateTime.now(),
            event.get().getId(),
            user.get().getId(),
            status);
        return requestStorage.save(newRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> getRequest(Long userId) {
        log.info("Call RequestServiceImpl getRequest with userId: {}", userId);
        return requestStorage.getAllByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Request> getByUserAndEventId(Long userId, Long eventId) {
        log.info("Call RequestServiceImpl getByUserAndEventId with userId: {}, eventId: {}", userId,
            eventId);
        return requestStorage.getByUserAndEventId(userId, eventId);
    }

    @Override
    @Transactional
    public Request cancelRequestByUser(Long userId, Long requestId) {
        log.info("Call RequestServiceImpl cancelRequestByUser with userId: {}, requestId: {}",
            userId, requestId);
        Request request = requestStorage.getReferenceById(requestId);
        request.setStatus(RequestState.CANCELED);
        return requestStorage.save(request);
    }

    @Override
    @Transactional
    public RequestUpdateResultDto updateRequestsStatus(Long userId, Long eventId,
        RequestUpdateDto requestUpdateDto)
        throws ConflictException, InvalidParameterException, NotFoundException {
        log.info("Call RequestServiceImpl updateRequestsStatus with userId: {}, eventId: {}, "
            + "RequestUpdateDto: {}", userId, eventId, requestUpdateDto);
        Optional<Event> event = eventStorage.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event not found id: " + eventId);
        }
        if (event.get().getConfirmedRequests() == null) {
            event.get().setConfirmedRequests(0);
        }
        List<Request> requests = requestStorage.getByRequestsList(requestUpdateDto.getRequestIds());

        RequestUpdateResultDto requestResultList = new RequestUpdateResultDto(
            new ArrayList<RequestDto>(), new ArrayList<RequestDto>());
        Integer confirmedRequestCount = event.get().getConfirmedRequests();
        if (confirmedRequestCount == null) {
            confirmedRequestCount = 0;
        }
        for (Request request : requests) {
            if (!request.getStatus().equals(RequestState.PENDING)) {
                throw new ConflictException("Change request with status is no PENDING");
            }
            if (requestUpdateDto.getStatus().equals(RequestUpdateState.CONFIRMED)) {
                if (event.get().getParticipantLimit() != 0 || !event.get().isRequestModeration()) {
                    if (event.get().getConfirmedRequests()
                        .equals(event.get().getParticipantLimit())) {
                        throw new ConflictException("Participants limit reached");
                    }
                    if (confirmedRequestCount < event.get().getParticipantLimit()) {
                        request.setStatus(RequestState.CONFIRMED);
                        event.get().setConfirmedRequests(event.get().getConfirmedRequests() + 1);
                        requestStorage.save(request);
                        requestResultList.getConfirmedRequests()
                            .add(requestMapper.toRequestDto(request));
                        confirmedRequestCount++;
                    } else {
                        request.setStatus(RequestState.REJECTED);
                        requestStorage.save(request);
                        requestResultList.getRejectedRequests()
                            .add(requestMapper.toRequestDto(request));
                    }
                }
            } else if (requestUpdateDto.getStatus().equals(RequestUpdateState.REJECTED)) {
                if (request.getStatus().equals(RequestState.CONFIRMED)) {
                    throw new ConflictException("Impossible to cancel approved request");
                }
                request.setStatus(RequestState.REJECTED);
                requestStorage.save(request);
                requestResultList.getRejectedRequests().add(requestMapper.toRequestDto(request));
            }
        }
        eventStorage.save(event.get());
        return requestResultList;
    }
}
