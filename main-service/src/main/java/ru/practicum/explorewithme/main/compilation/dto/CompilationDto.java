package ru.practicum.explorewithme.main.compilation.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.main.event.dto.EventShortDto;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationDto {
    Set<EventShortDto> events;
    Long id;
    Boolean pinned;
    String title;
}