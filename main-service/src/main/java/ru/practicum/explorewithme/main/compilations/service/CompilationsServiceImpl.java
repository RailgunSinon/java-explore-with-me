package ru.practicum.explorewithme.main.compilations.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.compilations.controller.mapper.CompilationMapper;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInfoDto;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInputDto;
import ru.practicum.explorewithme.main.compilations.model.Compilation;
import ru.practicum.explorewithme.main.compilations.storage.CompilationStorage;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.event.storage.EventStorage;

/*
Я видел в примерах, что маппер ДТО делают статикой. Хотя вроде как советуют статики в коде избегать
Насколько вообще правильно так делать?
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationsServiceImpl implements CompilationsService {

    private final CompilationStorage compilationStorage;
    private final EventStorage eventStorage;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional
    public CompilationInfoDto createCompilation(CompilationInputDto newCompilationDto)
        throws InvalidParameterException {
        log.info("Call CompilationsService createCompilation with newCompilationDto: {}",
            newCompilationDto);
        if (newCompilationDto.getTitle() == null) {
            throw new InvalidParameterException("Field Title is empty.");
        }
        if (newCompilationDto.getTitle().isBlank()) {
            throw new InvalidParameterException("Field Title is empty.");
        }
        if (newCompilationDto.getPinned() == null) {
            newCompilationDto.setPinned(false);
        }
        List<Event> events = eventStorage.findByIds(newCompilationDto.getEvents());
        return compilationMapper.toCompilationInfoDto(
            compilationStorage.save(compilationMapper.toCompilation(newCompilationDto, events)));
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        log.info("Call  CompilationsService deleteCompilation with compId: {}", compId);
        compilationStorage.findById(compId).orElseThrow();
        compilationStorage.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationInfoDto updateCompilation(Long compId, CompilationInputDto compilationDto) {
        log.info("Call  CompilationsService updateCompilation with compId: {}, "
            + "newCompilationDto: {}", compId, compilationDto);
        Compilation compilation = compilationStorage.findById(compId).orElseThrow();
        if (compilationDto.getEvents() != null) {
            compilation.setEvents(eventStorage.findByIds(compilationDto.getEvents()));
        }
        if (compilationDto.getPinned() != null) {
            compilation.setPinned(compilationDto.getPinned());
        }
        if (compilationDto.getTitle() != null) {
            compilation.setTitle(compilationDto.getTitle());
        }
        compilationStorage.save(compilation);
        return compilationMapper.toCompilationInfoDto(compilation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompilationInfoDto> findAllCompilations(Boolean pinned, Pageable pageable) {
        log.info("Call CompilationsService findAllCompilations with pinned: {}, pageable: {}",
            pinned, pageable);
        return compilationStorage.findAllByPinned(pinned, pageable).stream()
            .map(compilationMapper::toCompilationInfoDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationInfoDto findCompilationsById(Long compId) {
        log.info("Call CompilationsService findCompilationsById with compId: {}", compId);
        Compilation compilation = compilationStorage.findById(compId).orElseThrow();
        return compilationMapper.toCompilationInfoDto(compilation);
    }
}
