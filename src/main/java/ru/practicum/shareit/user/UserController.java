package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        log.info("POST-запрос на создание пользователя.");
        return userService.addUser(userDto);
    }
    @GetMapping("/{id}")
    public UserDto getUserByID(@PathVariable int id) {
        log.info("GET-запрос на вывод пользователя по идентификатору.");
        return userService.getById(id);
    }
    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("GET-запрос на вывод всех пользователей.");
        return userService.getAll();
    }
    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable int id, @RequestBody UserDto userDto) {
        log.info("PATCH-запрос на обновление пользователя по идентификатру.");
        return userService.updateById(id, userDto);
    }
    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable int id) {
        log.info("DELETE-запрос на удаление пользователя по идентификатру.");
        return userService.delete(id);
    }
}