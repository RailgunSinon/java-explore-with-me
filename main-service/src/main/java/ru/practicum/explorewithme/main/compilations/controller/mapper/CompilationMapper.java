package ru.practicum.explorewithme.main.compilations.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInfoDto;
import ru.practicum.explorewithme.main.compilations.dto.CompilationInputDto;
import ru.practicum.explorewithme.main.compilations.model.Compilation;
import ru.practicum.explorewithme.main.event.controller.mapper.EventMapper;
import ru.practicum.explorewithme.main.event.dto.EventInfoWithConfirmedDto;
import ru.practicum.explorewithme.main.event.model.Event;

@Component
@RequiredArgsConstructor
public class CompilationMapper {

    EventMapper eventMapper;

    public CompilationInfoDto toCompilationInfoDto(Compilation compilation) {
        List<EventInfoWithConfirmedDto> shortEvents = compilation.getEvents().stream()
            .map(e -> eventMapper.toEventDtoInfo(e))
            .collect(Collectors.toList());
        return new CompilationInfoDto(compilation.getId(),
            shortEvents,
            compilation.getPinned(),
            compilation.getTitle());
    }

    public Compilation toCompilation(CompilationInputDto newCompilationDto, List<Event> events) {
        return new Compilation(null,
            events,
            newCompilationDto.getPinned(),
            newCompilationDto.getTitle());
    }
}
