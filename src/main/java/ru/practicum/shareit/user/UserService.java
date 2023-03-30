package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserService {
    UserDto addUser(UserDto userDto);

    Collection<UserDto> getAll();

    UserDto delete(int id);

    UserDto getById(int id);

    UserDto updateById(int id, UserDto userDto);

    void checkUserId(int id);
}
