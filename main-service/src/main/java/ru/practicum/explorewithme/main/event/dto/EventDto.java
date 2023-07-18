package ru.practicum.explorewithme.main.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.event.model.enums.EventState;
import ru.practicum.explorewithme.main.user.dto.UserInfoDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {

    private Long id;

    private String title;

    private String description;

    private String annotation;

    private EventState state;

    private Category category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    private Integer confirmedRequests;

    private LocationDto location;

    private UserInfoDto initiator;

    private boolean paid;

    private Integer participantLimit;

    private boolean requestModeration;

    private Long views;
}
