package ru.practicum.explorewithme.main.user.controller;

import java.security.InvalidParameterException;
import java.util.List;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.ConflictException;
import ru.practicum.explorewithme.main.exceptionHandlers.exceptions.NotFoundException;
import ru.practicum.explorewithme.main.user.controller.mapper.UserMapper;
import ru.practicum.explorewithme.main.user.model.User;
import ru.practicum.explorewithme.main.user.service.UserService;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserAdminController {

    private final UserService userService;
    private final UserMapper mapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User addUser(@RequestBody @Valid User user)
        throws InvalidParameterException, ConflictException {
        log.info("Call AdminController addUser with userEmail {}, userName: {}: ",
            user.getEmail(), user.getName());
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getUsersByIds(@RequestParam(required = false) String ids,
        @RequestParam(defaultValue = "10") @Positive Integer size,
        @RequestParam(defaultValue = "0") @PositiveOrZero Integer from) {
        log.info("Call AdminController getUsersByIds with ids {}:  size {} from {} ", ids, size,
            from);
        return userService.getUserByIds(ids, from, size);
    }

    @PatchMapping("/{id}")
    public User updateUser(@Positive @PathVariable Long id, @Valid @RequestBody User user)
        throws ValidationException, NotFoundException {
        log.info("Call AdminController updateUser with userId {}: ", id);
        return userService.updateUser(id, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@Positive @PathVariable Long id)
        throws NotFoundException, InvalidParameterException {
        log.info("Call AdminController deleteUser with userId {}: ", id);
        userService.deleteUser(id);
    }
}
