package ru.practicum.explorewithme.main.comment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentUpdateDto {

    @NotBlank(message = "Текст комментария не может быть пустым")
    @Size(min = 1, max = 1000)
    String text;
}