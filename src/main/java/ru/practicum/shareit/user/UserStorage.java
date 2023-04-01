package ru.practicum.shareit.user;

import java.util.Collection;

public interface UserStorage {
    public User create(User user);
    public User get(int id);
    public Collection<User> getAll();
    public User update(User user);
    public User delete(int id);
}