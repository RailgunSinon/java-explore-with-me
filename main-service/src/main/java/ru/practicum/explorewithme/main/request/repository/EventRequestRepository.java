package ru.practicum.explorewithme.main.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import ru.practicum.explorewithme.main.enums.RequestStatus;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.request.model.EventRequest;
import ru.practicum.explorewithme.main.user.model.User;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    List<EventRequest> findAllByRequesterIdAndEventId(Long userId, Long eventId);

    List<EventRequest> findAllByEventId(Long eventId);

    List<EventRequest> findAllByRequester(User requester);

    List<EventRequest> findAllByIdIn(List<Long> ids);

    Long countByEventIdAndStatus(Long eventId, RequestStatus status);

    List<EventRequest> findAllByEventInAndStatus(List<Event> events, RequestStatus status);
}