package ru.practicum.explorewithme.main.user.controller;

import java.security.InvalidParameterException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.user.model.User;
import ru.practicum.explorewithme.main.user.service.UserService;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserPublicController {

    private final UserService userService;

    @PostMapping
    public User addUser(@RequestBody @Valid User user)
        throws InvalidParameterException, ConflictException {
        log.info("Call UserController addUser with userEmail {}, userName: {}: ",
            user.getEmail(), user.getName());
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getUsersByIds(@RequestParam(required = false) String ids,
        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
        @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Call UserController getUserByIds with ids {}:  size {} from {} ", ids, size,
            from);
        return userService.getUserByIds(ids, size, from);
    }

    @GetMapping("/{id}")
    public User getUserById(@Positive @PathVariable Long id) throws NotFoundException {
        log.info("Call UserController getUserById with id: {}", id);
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    public User updateUser(@Positive @PathVariable Long id, @Valid @RequestBody User user)
        throws ValidationException, NotFoundException {
        log.info("Call UserController updateUser with userId {}: ", id);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id)
        throws InvalidParameterException, NotFoundException {
        log.info("Call UserController deleteUser with userId {}: ", id);
        return userService.deleteUser(id);
    }
}
