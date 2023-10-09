package ru.practicum.explorewithme.main.event.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.user.model.User;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByInitiator(User user, Pageable pageable);

    Event findByInitiatorAndId(User user, Long id);

    List<Event> findAll(Specification<Event> specification, Pageable pageable);

    List<Event> findAllByCategoryId(Long catId);
}