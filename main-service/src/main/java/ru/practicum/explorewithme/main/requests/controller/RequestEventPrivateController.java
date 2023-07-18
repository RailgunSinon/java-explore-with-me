package ru.practicum.explorewithme.main.requests.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.requests.controller.mapper.RequestMapper;
import ru.practicum.explorewithme.main.requests.dto.RequestDto;
import ru.practicum.explorewithme.main.requests.dto.RequestUpdateDto;
import ru.practicum.explorewithme.main.requests.dto.RequestUpdateResultDto;
import ru.practicum.explorewithme.main.requests.service.RequestService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events/{eventId}/requests")
public class RequestEventPrivateController {

    private final RequestService requestService;
    private final RequestMapper mapper;

    @PatchMapping
    public RequestUpdateResultDto updateRequestsStatus(@PathVariable Long userId,
        @PathVariable Long eventId,
        @RequestBody @Valid RequestUpdateDto requestUpdateDto)
        throws ConflictException, NotFoundException {
        log.info("Call EventPrivateRequestController updateRequestsStatus with userId {}, "
            + "eventId: {}, requestUpdateDto: {}", userId, eventId, requestUpdateDto);
        return requestService.updateRequestsStatus(userId, eventId, requestUpdateDto);
    }

    @GetMapping
    public List<RequestDto> getByUserAndEventId(@PathVariable Long userId,
        @PathVariable Long eventId) {
        log.info(
            "Call EventPrivateRequestController getByUserAndEventId with userId {}, eventId: {}",
            userId, eventId);
        return mapper.toRequestDtoList(requestService.getByUserAndEventId(userId, eventId));
    }
}
