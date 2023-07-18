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
import ru.practicum.explorewithme.main.user.controller.mapper.UserMapper;
import ru.practicum.explorewithme.main.user.dto.UserDto;
import ru.practicum.explorewithme.main.user.service.UserService;

@Validated
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserPublicController {

    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto)
        throws InvalidParameterException, ConflictException {
        log.info("Call UserController addUser with userEmail {}, userName: {}: ",
            userDto.getEmail(),
            userDto.getName());
        return mapper.toUserDto(userService.addUser(mapper.toUser(userDto)));
    }

    @GetMapping
    public List<UserDto> getUsersByIds(@RequestParam(required = false) String ids,
        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
        @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Call UserController getUserByIds with ids {}:  size {} from {} ", ids, size,
            from);
        return mapper.toUserDtoList(userService.getUserByIds(ids, size, from));
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@Positive @PathVariable Long id) throws NotFoundException {
        log.info("Call UserController getUserById with id: {}", id);
        return mapper.toUserDto(userService.getUser(id));
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@Positive @PathVariable Long id, @Valid @RequestBody UserDto userDto)
        throws ValidationException, NotFoundException {
        log.info("Call UserController updateUser with userId {}: ", id);
        return mapper.toUserDto(userService.updateUser(id, mapper.toUser(userDto)));
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id)
        throws InvalidParameterException, NotFoundException {
        log.info("Call UserController deleteUser with userId {}: ", id);
        return mapper.toUserDto(userService.deleteUser(id));
    }
}
