package ru.practicum.explorewithme.main.user.service;

import java.security.InvalidParameterException;
import java.util.List;
import javax.validation.ValidationException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.user.model.User;

public interface UserService {

    User addUser(User user) throws InvalidParameterException, ConflictException;

    List<User> getUserByIds(String ids, Integer size, Integer from);

    User getUser(Long userId) throws NotFoundException;

    User updateUser(Long userId, User user) throws NotFoundException, ValidationException;

    User deleteUser(Long userId) throws NotFoundException, InvalidParameterException;
}
