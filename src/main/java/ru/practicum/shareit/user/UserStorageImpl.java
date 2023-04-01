package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserStorageImpl implements UserStorage{
    private Map<Integer, User> users = new HashMap<>();
    private int id;

    public User create(User user) {
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    public User get(int id) {
        return users.get(id);
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    public User delete(int id) {
        User user = get(id);
        users.remove(id);
        return user;
    }

}
