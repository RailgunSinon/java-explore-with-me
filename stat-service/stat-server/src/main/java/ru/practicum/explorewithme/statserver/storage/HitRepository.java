package ru.practicum.explorewithme.statserver.storage;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.statserver.model.Hit;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {

    @Query("SELECT h FROM Hit h WHERE h.created BETWEEN ?1 AND ?2 AND (h.uri IN ?3 OR ?3 IS NULL) "
        + "ORDER BY h.app,h.uri,h.ip")
    List<Hit> findHits(LocalDateTime start, LocalDateTime end, List<String> uris);
}
