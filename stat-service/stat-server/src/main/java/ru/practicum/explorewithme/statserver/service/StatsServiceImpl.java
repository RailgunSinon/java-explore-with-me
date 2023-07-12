package ru.practicum.explorewithme.statserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
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
   /* private EntityManagerFactory entityManagerFactory
        = Persistence.createEntityManagerFactory("stats");*/

    @Override
    @Transactional
    public StateHitDto hit(StateHitDto stateHitDto) {
        Hit hit = hitMapper.toEntity(stateHitDto);
        return hitMapper.toDto(hitRepository.save(hit));
    }
/*
    @Override
    @Transactional(readOnly = true)
    public List<StateViewDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<StateViewDto> statResult;
        Query query;
        if (unique.booleanValue() == true) {
            query = entityManager.createQuery("SELECT h.app,h.uri,COUNT(DISTINCT (h.ip)) "
                + "FROM Hit h "
                + "WHERE h.created BETWEEN ?1 AND ?2 AND (h.uri IN ?3) "
                + "GROUP BY h.app,h.uri "
                + "ORDER BY h.app,h.uri");
        } else {
            query = entityManager.createQuery("SELECT h.app,h.uri,COUNT(h.ip) "
                + "FROM Hit h "
                + "WHERE h.created BETWEEN ?1 AND ?2 AND (h.uri IN ?3) "
                + "GROUP BY h.app,h.uri "
                + "ORDER BY h.app,h.uri");
        }
        query.setParameter(1, start).setParameter(2, end).setParameter(3, uris);
        statResult = query.getResultList();
        statResult = statResult.stream()
                     .sorted(Comparator.comparingLong(StateViewDto::getHits).reversed())
                     .collect(Collectors.toList());

        return statResult;
    }*/


    @Override
    @Transactional(readOnly = true)
    public List<StateViewDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique) {
        List<StateViewDto> stateViewDtos;
        if(unique.booleanValue() == true){
            stateViewDtos = hitRepository.findHitsUniq(start,end,uris);
        }
        else {
            stateViewDtos = hitRepository.findHitsNoUniq(start,end,uris);
        }

        stateViewDtos = stateViewDtos.stream()
            .sorted(Comparator.comparingLong(StateViewDto::getHits).reversed())
            .collect(Collectors.toList());

        return stateViewDtos;
    }

    /*
    @Override
    @Transactional(readOnly = true)
    public List<StateViewDto> stats(LocalDateTime start, LocalDateTime end, List<String> uris,
        Boolean unique) {
        List<Hit> hits = hitRepository.findHits(start, end, uris);
        List<StateViewDto> stateViewDtos = new ArrayList<>();

        Integer i = 0;
        while (i < hits.size()) {
            StateViewDto viewStateDto = new StateViewDto(hits.get(i).getApp(), hits.get(i).getUri(),
                1L);
            Integer code = createViewStateDto(1, viewStateDto, hits, i + 1, unique);
            stateViewDtos.add(viewStateDto);
            i += code;
        }
        stateViewDtos = stateViewDtos.stream()
            .sorted(Comparator.comparingLong(StateViewDto::getHits).reversed())
            .collect(Collectors.toList());

        return stateViewDtos;

    }*/

    private Integer createViewStateDto(Integer code, StateViewDto stateViewDto, List<Hit> hits,
        Integer i, Boolean unique) {
        if (i >= hits.size()) {
            return code;
        }

        Hit prevHit = hits.get(i - 1);
        Hit curHit = hits.get(i);

        if (curHit.getApp().equals(prevHit.getApp()) && curHit.getUri().equals(prevHit.getUri())) {
            if (!unique || !curHit.getIp().equals(prevHit.getIp())) {
                stateViewDto.setHits(stateViewDto.getHits() + 1);
            }
            return createViewStateDto(code + 1, stateViewDto, hits, i + 1, unique);
        }

        return code;
    }

}
