package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidateException;

import java.util.Collection;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = UserMapper.toUser(userDto);
        checkEmail(user);
        return UserMapper.toUserDto(userStorage.add(user));
    }

    @Override
    public Collection<UserDto> getAll() {
        return userStorage.getAll().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto delete(int id) {
        return UserMapper.toUserDto(userStorage.delete(id));
    }

    @Override
    public UserDto getById(int id) {
        return UserMapper.toUserDto(userStorage.getById(id));
    }

    @Override
    public UserDto updateById(int id, UserDto userDto) {
        User newUser = UserMapper.toUser(userDto);
        checkDuplicateEmail(newUser);
        User user = UserMapper.toUser(getById(id));
        if (newUser.getEmail() != null)
            user.setEmail(newUser.getEmail());
        if (newUser.getName() != null)
            user.setName(newUser.getName());
        return UserMapper.toUserDto(userStorage.update(user));
    }


    void checkEmail(User user) {
        checkDuplicateEmail(user);
        if (user.getEmail() == null || user.getEmail().isBlank() || (!user.getEmail().contains("@"))) {
            log.info("Неверно указана электронная почта");
            throw new ValidateException("Неверно указана электронная почта");
        }
    }

    void checkDuplicateEmail(User user) {
        for (User user1 : userStorage.getAll()) {
            if (user1.getEmail().equals(user.getEmail())) {
                log.info("Пользователь с таким Email уже существует");
                throw new DuplicateEmailException("Пользователь с таким Email уже существует");
            }
        }
    }

    public void checkUserId(int id) {
        if (getAll().stream().noneMatch(u -> u.getId() == id))
            throw new NotFoundException("Пользователь с таким ID не найден");
    }
}