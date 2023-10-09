package ru.practicum.explorewithme.main.category.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCategoryDto {

    @NotNull(message = "Имя категории должно быть задано")
    @NotBlank(message = "Имя категории не может быть пустым")
    @Size(max = 50, message = "Имя категории не должно превышать 50 символов")
    String name;
}