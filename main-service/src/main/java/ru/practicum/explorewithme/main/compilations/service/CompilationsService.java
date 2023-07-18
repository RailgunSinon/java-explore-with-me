package ru.practicum.explorewithme.main.compilations.service;

import java.security.InvalidParameterException;
import java.util.List;
import org.springframework.data.domain.Pageable;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInfoDto;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInputDto;

public interface CompilationsService {

    CompilationInfoDto createCompilation(CompilationInputDto newCompilationDto)
        throws InvalidParameterException;

    void deleteCompilation(Long compId);

    CompilationInfoDto updateCompilation(Long compId, CompilationInputDto newCompilationDto);

    List<CompilationInfoDto> findAllCompilations(Boolean pinned, Pageable pageable);

    CompilationInfoDto findCompilationsById(Long compId);
}
