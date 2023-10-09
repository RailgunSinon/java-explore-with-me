package ru.practicum.explorewithme.main.compilation.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Set;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationUpdateDto {
    Set<Long> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}