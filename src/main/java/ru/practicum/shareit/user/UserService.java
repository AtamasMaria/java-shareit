
package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        validateEmail(user);
        return UserMapper.toUserDto(userStorage.create(user));
    }

    public Collection<UserDto> getAll() {
        return userStorage.getAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto getById(int id) {
        return UserMapper.toUserDto(userStorage.get(id));
    }

    public UserDto updateById(int id, UserDto userDto) {
        User newUser = UserMapper.toUser(userDto);
        checkDuplicateEmail(newUser);
        User user = userStorage.get(id);
        if (newUser.getEmail() != null)
            user.setEmail(newUser.getEmail());
        if (newUser.getName() != null)
            user.setName(newUser.getName());
        return UserMapper.toUserDto(userStorage.update(user));
    }

    public UserDto delete(int id) {
        return UserMapper.toUserDto(userStorage.delete(id));
    }

    void validateEmail(User user) {
        checkDuplicateEmail(user);
        if (user.getEmail() == null || user.getEmail().isBlank() || (!user.getEmail().contains("@"))) {
            throw new ValidateException("Неверно заполнено полу эл.почта");
        }
    }

    void checkDuplicateEmail(User user) {
        if (userStorage.getAll().stream().anyMatch(u -> u.getEmail().equals(user.getEmail())
                && u.getId() != user.getId())) {
            throw new DuplicateEmailException("Пользователь с таким Email уже существует");
        }
    }

    public void checkUserId(int id) {
        if (getAll().stream().noneMatch(user -> user.getId() == id))
            throw new NotFoundException("Пользователь с таким ID не найден");
    }
}