package ru.practicum.explorewithme.statserver.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatDto;
import ru.practicum.explorewithme.statserver.service.StatsService;

@RequiredArgsConstructor
@RestController
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto addHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Информация сохранена {}", hitDto);
        return statsService.addHit(hitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStatistics(
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
        @RequestParam(required = false) List<String> uris,
        @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Статистика собрана: start {}, end {}", start, end);
        return statsService.getStatistics(start, end, uris, unique);
    }

}
