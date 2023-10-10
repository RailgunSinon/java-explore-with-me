package ru.practicum.explorewithme.main.comment.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {

    Long id;
    String text;
    Long commentatorId;
    Long eventId;
    LocalDateTime created;
    LocalDateTime updated;
}