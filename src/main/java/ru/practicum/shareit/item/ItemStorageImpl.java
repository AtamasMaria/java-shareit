package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemStorageImpl implements ItemStorage {
    private final Map<Integer, Item> items = new HashMap<>();
    private int id;

    @Override
    public Item create(Item item) {
        item.setId(++id);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item get(int id) {
        return items.get(id);
    }

    @Override
    public Collection<Item> getAllByOwnerId(int ownerId) {
        return items.values().stream()
                .filter(item -> item.getOwner() == ownerId)
                .collect(Collectors.toList());
    }
    @Override
    public Item update(Item item) {
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Collection<Item> search(String text) {
        if (text.equals(""))
            return new ArrayList<>();
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::getAvailable)
                .collect(Collectors.toList());
    }
}
