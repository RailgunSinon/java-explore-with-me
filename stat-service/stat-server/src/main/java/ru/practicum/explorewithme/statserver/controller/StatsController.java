package ru.practicum.explorewithme.statserver.controller;

import static ru.practicum.explorewithme.statserver.GlobalStaticProperties.DATE_FORMAT;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.dto.StateHitDto;
import ru.practicum.explorewithme.dto.StateViewDto;
import ru.practicum.explorewithme.statserver.service.StatsService;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @PostMapping("/hit")
    public ResponseEntity<StateHitDto> hit(@RequestBody @Valid StateHitDto stateHitDto) {
        log.info("Save hit {}", stateHitDto);
        return new ResponseEntity<>(statsService.hit(stateHitDto), HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<StateViewDto>> stats(@RequestParam String start,
        @RequestParam String end,
        @RequestParam(required = false) List<String> uris,
        @RequestParam(defaultValue = "false") Boolean unique) throws UnsupportedEncodingException {
        log.info("Request stats start={}, end={}, uris={}, unique={}", start, end, uris, unique);

        start = URLDecoder.decode(start, StandardCharsets.UTF_8.toString());
        end = URLDecoder.decode(end, StandardCharsets.UTF_8.toString());

        List<StateViewDto> stats = statsService.stats(LocalDateTime.parse(start, DATE_FORMAT),
            LocalDateTime.parse(end, DATE_FORMAT), uris, unique);

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
