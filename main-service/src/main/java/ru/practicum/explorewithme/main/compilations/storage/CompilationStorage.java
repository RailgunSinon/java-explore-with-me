package ru.practicum.explorewithme.main.compilations.storage;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explorewithme.main.compilations.model.Compilation;

public interface CompilationStorage extends JpaRepository<Compilation, Long> {

    @Query("SELECT c " +
        "FROM Compilation AS c " +
        "WHERE :pinned IS NULL OR c.pinned = :pinned")
    List<Compilation> findAllByPinned(@Param("pinned") Boolean pinned, Pageable pageable);
}
