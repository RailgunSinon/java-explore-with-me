package ru.practicum.explorewithme.main.event.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.user.dto.UserShortDto;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {

    String annotation;
    CategoryDto category;
    Long confirmedRequests;
    String eventDate;
    Long id;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Long views;
}