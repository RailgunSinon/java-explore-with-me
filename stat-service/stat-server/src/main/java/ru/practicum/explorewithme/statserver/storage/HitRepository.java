package ru.practicum.explorewithme.statserver.storage;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.dto.StateViewDto;
import ru.practicum.explorewithme.statserver.model.Hit;

@Repository
public interface HitRepository extends JpaRepository<Hit, Long> {
    
    @Query("SELECT new ru.practicum.explorewithme.dto.StateViewDto(h.app,h.uri,COUNT(h.ip)) "
        + "FROM Hit h WHERE h.created BETWEEN ?1 AND ?2 AND (h.uri IN ?3 OR ?3 IS NULL) "
        + "GROUP BY h.app,h.uri ORDER BY COUNT(h.ip) DESC")
    List<StateViewDto> findHitsNoUniq(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("SELECT new ru.practicum.explorewithme.dto.StateViewDto(h.app,h.uri,COUNT(DISTINCT(h.ip))) "
        + "FROM Hit h WHERE h.created BETWEEN ?1 AND ?2 AND (h.uri IN ?3 OR ?3 IS NULL) "
        + "GROUP BY h.app,h.uri ORDER BY COUNT(DISTINCT(h.ip)) DESC ")
    List<StateViewDto> findHitsUniq(LocalDateTime start, LocalDateTime end, List<String> uris);
}
