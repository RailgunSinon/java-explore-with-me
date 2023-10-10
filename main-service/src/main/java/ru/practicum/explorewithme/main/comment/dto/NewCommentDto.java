package ru.practicum.explorewithme.main.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCommentDto {

    @NotNull
    Long eventId;

    @NotBlank(message = "Текст комментария не может быть пустым")
    @Size(min = 1, max = 1000)
    String text;
}