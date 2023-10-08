package ru.practicum.explorewithme.main.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.event.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}