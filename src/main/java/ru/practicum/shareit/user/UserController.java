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
        log.info("POST-запрос на добавление пользователя.");
        return userService.addUser(userDto);
    }

    @GetMapping
    public Collection<UserDto> getAllUsers() {
        log.info("GET-запрос на вывод пользователей");
        return userService.getAll();
    }


    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable int id) {
        log.info("DELETE-запрос на удаление пользователя");
        return userService.delete(id);
    }

    @GetMapping("/{id}")
    public UserDto getUserByID(@PathVariable int id) {
        log.info("GET-запрос на вывод пользователя по id");
        return userService.getById(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUserById(@PathVariable int id, @RequestBody UserDto userDto) {
        log.info("PATCH-запрос на обновление пользователя по ID");
        return userService.updateById(id, userDto);
    }
}