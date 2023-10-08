package ru.practicum.explorewithme.main.event.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.enums.EventState;
import ru.practicum.explorewithme.main.user.dto.UserShortDto;

@Data
@Builder
public class EventDto {

    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    private Long id;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private Long participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private String title;
    private Long views;
}