package ru.practicum.explorewithme.main.user.controller.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.explorewithme.main.user.dto.UserDto;
import ru.practicum.explorewithme.main.user.dto.UserShortDto;
import ru.practicum.explorewithme.main.user.model.User;

@UtilityClass
public class UserMapper {

    public static User toUser(UserDto userDto) {
        return User.builder()
            .email(userDto.getEmail())
            .name(userDto.getName())
            .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
            .email(user.getEmail())
            .id(user.getId())
            .name(user.getName())
            .build();
    }

    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
    }
}