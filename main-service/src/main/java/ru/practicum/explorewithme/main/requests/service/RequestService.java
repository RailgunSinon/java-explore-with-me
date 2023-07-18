package ru.practicum.explorewithme.main.requests.service;

import java.security.InvalidParameterException;
import java.util.List;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.requests.dto.RequestUpdateDto;
import ru.practicum.explorewithme.main.requests.dto.RequestUpdateResultDto;
import ru.practicum.explorewithme.main.requests.model.Request;

public interface RequestService {

    Request createRequest(Long userId, Long eventId) throws ConflictException, NotFoundException;

    List<Request> getRequest(Long id);

    List<Request> getByUserAndEventId(Long userId, Long eventId);

    Request cancelRequestByUser(Long userId, Long requestId);

    RequestUpdateResultDto updateRequestsStatus(Long userId, Long eventId,
        RequestUpdateDto requestUpdateDto)
        throws ConflictException, InvalidParameterException, NotFoundException;
}
