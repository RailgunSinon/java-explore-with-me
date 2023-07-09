package ru.practicum.explorewithme.statserver.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.explorewithme.dto.StateHitDto;
import ru.practicum.explorewithme.dto.StateViewDto;

public interface StatsService {

    StateHitDto hit(StateHitDto stateHitDto);

    List<StateViewDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique);
}
