package ru.practicum.explorewithme.main.compilations.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.main.event.dto.EventInfoWithConfirmedDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationInfoDto {

    private Long id;
    private List<EventInfoWithConfirmedDto> events;
    private Boolean pinned;
    private String title;
}
