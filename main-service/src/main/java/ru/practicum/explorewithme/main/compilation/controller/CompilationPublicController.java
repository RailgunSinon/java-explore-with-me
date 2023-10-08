package ru.practicum.explorewithme.main.compilation.controller;

import java.util.Collection;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilation.service.CompilationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/compilations")
public class CompilationPublicController {

    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilationById(@PathVariable final Long compId) {
        return compilationService.getCompilationById(compId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<CompilationDto> getAllCompilations(
        @RequestParam(required = false) Boolean pinned,
        @RequestParam(value = "from", required = false, defaultValue = "0")
        @PositiveOrZero(message = "Значение 'from' должно быть положительным") final Integer from,
        @RequestParam(value = "size", required = false, defaultValue = "10")
        @Positive(message = "Значение 'size' должно быть положительным") final Integer size) {

        return compilationService.getAllCompilations(pinned, from, size);
    }
}