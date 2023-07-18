package ru.practicum.explorewithme.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explorewithme.main.category.dto.CategoryDto;
import ru.practicum.explorewithme.main.user.dto.UserInfoDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventInfoWithConfirmedDto {

    private Long id;

    private String annotation;

    private CategoryDto category;

    private Integer confirmedRequests;

    private String eventDate;

    private UserInfoDto initiator;

    private Boolean paid;

    private String title;

    private Long views;
}
