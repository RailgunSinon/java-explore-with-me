package ru.practicum.explorewithme.main.event.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.event.model.Location;

public interface LocationStorage extends JpaRepository<Location, Long> {

}
