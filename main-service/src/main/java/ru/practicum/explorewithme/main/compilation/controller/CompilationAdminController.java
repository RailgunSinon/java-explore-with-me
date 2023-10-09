package ru.practicum.explorewithme.main.compilation.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilation.dto.CompilationUpdateDto;
import ru.practicum.explorewithme.main.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.main.compilation.service.CompilationService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/compilations")
public class CompilationAdminController {

    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addNewCompilation(
        @RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationService.addNewCompilation(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilation(@PathVariable final Long compId,
        @RequestBody @Valid CompilationUpdateDto compilationUpdateDto) {
        return compilationService.updateCompilationInfo(compId, compilationUpdateDto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable final Long compId) {
        compilationService.deleteCompilation(compId);
    }
}