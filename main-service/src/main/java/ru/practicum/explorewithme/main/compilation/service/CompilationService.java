package ru.practicum.explorewithme.main.compilation.service;


import java.util.Collection;
import ru.practicum.explorewithme.main.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilation.dto.CompilationUpdateDto;
import ru.practicum.explorewithme.main.compilation.dto.NewCompilationDto;

public interface CompilationService {

    CompilationDto addNewCompilation(NewCompilationDto newCompilationDto);

    CompilationDto getCompilationById(Long compilationId);

    CompilationDto updateCompilationInfo(Long compilationId,
        CompilationUpdateDto newCompilationInfo);

    void deleteCompilation(Long compilationId);

    Collection<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size);
}