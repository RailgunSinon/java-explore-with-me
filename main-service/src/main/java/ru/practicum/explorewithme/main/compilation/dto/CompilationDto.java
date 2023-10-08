package ru.practicum.explorewithme.main.compilation.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import ru.practicum.explorewithme.main.event.dto.EventShortDto;

@Data
@Builder
public class CompilationDto {
    Set<EventShortDto> events;
    Long id;
    Boolean pinned;
    String title;
}