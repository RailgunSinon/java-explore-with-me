package ru.practicum.explorewithme.main.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.user.dto.UserInfoDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInfoWithDescriptionDto {

    private Long id;

    private String title;

    private String description;

    private String annotation;

    private Category category;

    private String eventDate;

    private UserInfoDto initiator;

    private boolean paid;

    private int views;

}
