package ru.practicum.explorewithme.main.requests.controller;

import java.util.List;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.requests.controller.mapper.RequestMapper;
import ru.practicum.explorewithme.main.requests.dto.RequestDto;
import ru.practicum.explorewithme.main.requests.service.RequestService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/requests")
public class RequestPrivateController {

    private final RequestService requestService;
    private final RequestMapper mapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RequestDto addRequest(@PathVariable @Positive Long userId,
        @RequestParam @Positive Long eventId) throws ConflictException, NotFoundException {
        log.info("Call  RequestPrivateController addRequest with userId {}, eventId: {}", userId,
            eventId);
        return mapper.toRequestDto(requestService.createRequest(userId, eventId));
    }

    @GetMapping
    public List<RequestDto> getRequests(@PathVariable Long userId) {
        log.info("Call  RequestPrivateController getRequest with userId {}", userId);
        return mapper.toRequestDtoList(requestService.getRequest(userId));
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequestByUser(@PathVariable Long userId, @PathVariable Long requestId) {
        log.info("Call RequestPrivateController cancelRequestByUser with userId {}, requestId: {}",
            userId, requestId);
        return mapper.toRequestDto(requestService.cancelRequestByUser(userId, requestId));
    }
}
