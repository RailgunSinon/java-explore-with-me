package ru.practicum.explorewithme.statserver.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatDto;
import ru.practicum.explorewithme.statserver.controller.mapper.HitMapper;
import ru.practicum.explorewithme.statserver.controller.mapper.StatMapper;
import ru.practicum.explorewithme.statserver.exceptionHandlers.errors.BadRequestException;
import ru.practicum.explorewithme.statserver.model.Stat;
import ru.practicum.explorewithme.statserver.storage.StatRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {

    private final StatRepository statRepository;

    @Override
    @Transactional
    public HitDto addHit(HitDto hitDto) {
        return HitMapper.toDto(statRepository.save(HitMapper.toHit(hitDto)));

    }

    @Override
    @Transactional(readOnly = true)
    public List<StatDto> getStatistics(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique) {
        if (start.isAfter(end)) {
            throw new BadRequestException("Время окончания должно быть позднее времени начала!");
        }
        List<Stat> stat = (uris == null || uris.isEmpty())
            ? unique
            ? statRepository.findAllUniqueStats(start, end)
            : statRepository.findAllStats(start, end)
            : unique
                ? statRepository.findUniqueStat(start, end, uris)
                : statRepository.findStat(start, end, uris);

        return stat.stream().map(StatMapper::toStatDto).collect(Collectors.toList());
    }
}
