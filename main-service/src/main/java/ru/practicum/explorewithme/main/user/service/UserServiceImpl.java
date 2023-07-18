package ru.practicum.explorewithme.main.user.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.user.model.User;
import ru.practicum.explorewithme.main.user.storage.UserStorage;
import ru.practicum.explorewithme.main.utility.GeneralUtility;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;

    @Override
    @Transactional
    public User addUser(User user) throws InvalidParameterException, ConflictException {
        log.info("Call UserServiceImpl addUser with userEmail: {}, userName: {}", user.getEmail(),
            user.getName());
        if (userStorage.existsUserByEmail(user.getEmail())) {
            throw new ConflictException("Already exists user with email: " + user.getEmail());
        }
        return userStorage.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUserByIds(String ids, Integer size, Integer from) {
        log.info("Call UserServiceImpl getUserByIds with userIds: {}, from: {}, size: {}", ids,
            from, size);
        PageRequest page = PageRequest.of(from / size, size);
        List<Long> usersIds = GeneralUtility.convertToLongFromString(ids);

        if (usersIds != null) {
            return userStorage.findAllByIdIn(usersIds, page);
        } else {
            return userStorage.findAll(page).stream().collect(Collectors.toList());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(Long userId) throws NotFoundException {
        log.info("Call UserServiceImpl getUser with UserId: {}", userId);
        Optional<User> user = userStorage.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException("Нет пользователя с id: " + userId);
        }
        return user.get();
    }

    @Override
    @Transactional
    public User updateUser(Long userId, User user) throws NotFoundException, ValidationException {
        log.info("Call UserServiceImpl updateUser with UserId: {}, userName: {}", userId,
            user.getName());
        Optional<User> updateUser = userStorage.findById(user.getId());
        if (updateUser.isEmpty()) {
            throw new NotFoundException("User not found, id: " + user.getId());
        }
        if ((user.getEmail() == null || user.getEmail().isBlank())) {
            user.setEmail(updateUser.get().getEmail());
        }
        if ((user.getName() == null || user.getName().isBlank())) {
            user.setName(updateUser.get().getName());
        }
        if (!updateUser.get().getEmail().equals(user.getEmail()) && (userStorage
            .existsUserByEmail(user.getEmail()))) {
            throw new ValidationException(
                "The еmail: " + user.getEmail() + " already used!");
        }
        return userStorage.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(Long userId) throws NotFoundException, InvalidParameterException {
        log.info("Call UserServiceImpl deleteUser with UserId: {}", userId);
        if (userId <= 0) {
            throw new InvalidParameterException(
                "id should be a positive number, yours is " + userId);
        }
        Optional<User> deleteUser = userStorage.findById(userId);
        if (deleteUser.isEmpty()) {
            throw new NotFoundException("User not found, id: " + userId);
        }
        userStorage.deleteById(userId);
        return deleteUser.get();
    }

}
