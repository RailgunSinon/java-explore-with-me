package ru.practicum.explorewithme.client.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.explorewithme.client.dto.StateHitDto;
import ru.practicum.explorewithme.client.dto.StateViewDto;

public interface StatsService {

    StateHitDto hit(StateHitDto stateHitDto);

    List<StateViewDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique);
}
