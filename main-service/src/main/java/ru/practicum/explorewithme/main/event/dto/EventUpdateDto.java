package ru.practicum.explorewithme.main.event.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.event.model.Location;
import ru.practicum.explorewithme.main.event.model.enums.StateAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDto {

    private Long id;

    @Size(max = 120)
    @Size(min = 3)
    private String title;

    @Size(max = 7000)
    @Size(min = 20)
    private String description;

    @Size(max = 2000)
    @Size(min = 20)
    private String annotation;

    private Long category;

    private String eventDate;

    private Location location;

    private String paid;

    private Integer participantLimit;

    private String requestModeration;

    private StateAction stateAction;
}
