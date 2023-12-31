package ru.practicum.explorewithme.main.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    Long id;
    @NotBlank(message = "Email пользователя не может быть пустым")
    @Email
    @Size(min = 6, max = 254)
    String email;
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 2, max = 250)
    String name;
}