package ru.practicum.explorewithme.main.compilations.dto;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationInputDto {

    private List<Long> events;
    private Boolean pinned;

    @Size(max = 50)
    private String title;
}
