package ru.practicum.explorewithme.main.user.service;


import java.util.Collection;
import java.util.List;
import ru.practicum.explorewithme.main.user.dto.UserDto;

public interface UserService {

    UserDto addUser(UserDto userDto);

    void deleteUser(Long id);

    Collection<UserDto> getAllUsers(List<Long> ids, Integer from, Integer size);
}
