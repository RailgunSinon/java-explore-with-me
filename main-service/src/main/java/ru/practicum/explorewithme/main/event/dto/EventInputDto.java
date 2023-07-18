package ru.practicum.explorewithme.main.event.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.event.model.Location;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInputDto {

    private Long id;

    @NotEmpty
    @Size(max = 120)
    @Size(min = 3)
    private String title;

    @NotEmpty
    @Size(max = 7000)
    @Size(min = 20)
    private String description;

    @NotEmpty
    @Size(max = 2000)
    @Size(min = 20)
    private String annotation;

    private Long category;

    private String eventDate;

    private Location location;

    private boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;
}
