package ru.practicum.explorewithme.main.user.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.main.user.dto.UserDto;
import ru.practicum.explorewithme.main.user.dto.UserInfoDto;
import ru.practicum.explorewithme.main.user.model.User;

/*
Тут порекомендовали вместо Entity писать имя самой сущности
*/
@Component
public class UserMapper {

    public UserInfoDto toUserInfoDto(User user) {
        return new UserInfoDto(
            user.getId(),
            user.getName()
        );
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }

    public User toUser(UserDto userDto) {
        return new User(
            userDto.getId(),
            userDto.getName(),
            userDto.getEmail()
        );
    }

    public List<UserDto> toUserDtoList(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(toUserDto(user));
        }
        return userDtos;
    }
}
