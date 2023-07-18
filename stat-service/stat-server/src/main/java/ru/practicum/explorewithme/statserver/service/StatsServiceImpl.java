package ru.practicum.explorewithme.statserver.service;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.StateHitDto;
import ru.practicum.explorewithme.dto.StateViewDto;
import ru.practicum.explorewithme.statserver.controller.mapper.HitMapper;
import ru.practicum.explorewithme.statserver.model.Hit;
import ru.practicum.explorewithme.statserver.storage.HitRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final HitRepository hitRepository;
    private final HitMapper hitMapper;

    @Override
    @Transactional
    public StateHitDto hit(StateHitDto stateHitDto) {
        Hit hit = hitMapper.toEntity(stateHitDto);
        return hitMapper.toDto(hitRepository.save(hit));
    }

    @Override
    @Transactional(readOnly = true)
    public List<StateViewDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique) {
        if (start.isAfter(end) || start == null || end == null) {
            throw new ValidationException("Bad start or end date!");
        }
        return unique.booleanValue() == true ? hitRepository.findHitsUniq(start, end, uris)
            : hitRepository.findHitsNoUniq(start, end, uris);
    }
}
