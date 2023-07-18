package ru.practicum.explorewithme.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.event.model.enums.StateAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateUserRequestDto {

    private String annotation;

    private Integer category;

    private String description;

    private String eventDate;

    private LocationDto location;

    private boolean paid;

    private Integer participantLimit;

    private boolean requestModeration;

    private StateAction stateAction;

    private String title;
}
