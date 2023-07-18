package ru.practicum.explorewithme.main.compilations.controller;

import java.security.InvalidParameterException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInfoDto;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInputDto;
import ru.practicum.explorewithme.main.compilations.service.CompilationsService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationsController {

    private final CompilationsService compilationsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationInfoDto addCompilation(
        @Valid @RequestBody CompilationInputDto newCompilationDto)
        throws InvalidParameterException {
        log.info("Call AdminCompilationsController addCompilation with compilationNewDto: {}",
            newCompilationDto);
        return compilationsService.createCompilation(newCompilationDto);
    }

    @PatchMapping("/{id}")
    public CompilationInfoDto updateCompilation(@PathVariable Long id,
        @Valid @RequestBody CompilationInputDto newCompilationDto) {
        log.info("Call AdminCompilationsController updateCompilation with CompilationId: {}", id);
        return compilationsService.updateCompilation(id, newCompilationDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long id) {
        log.info("Call AdminCompilationsController deleteCompilation with CompilationId: {}", id);
        compilationsService.deleteCompilation(id);
    }
}
