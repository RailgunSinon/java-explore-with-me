package ru.practicum.explorewithme.statserver.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatDto;

public interface StatsService {

    HitDto addHit(HitDto hitDto);

    List<StatDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique);
}
