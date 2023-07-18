package ru.practicum.explorewithme.main.compilations.controller;

import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInfoDto;
import ru.practicum.explorewithme.main.compilations.service.CompilationsService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationsController {

    private final CompilationsService compilationsService;

    @GetMapping
    public List<CompilationInfoDto> findAll(@RequestParam(required = false) Boolean pinned,
        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
        @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Call PublicCompilationsController findAll with pinned {} size {} from {} ",
            pinned, size, from);
        PageRequest pageable = PageRequest.of(from / size, size);
        return compilationsService.findAllCompilations(pinned, pageable);
    }

    @GetMapping("/{compId}")
    public CompilationInfoDto findById(@PathVariable Long compId) {
        log.info("Call PublicCompilationsController findById with CompilationId {}", compId);
        return compilationsService.findCompilationsById(compId);
    }
}
