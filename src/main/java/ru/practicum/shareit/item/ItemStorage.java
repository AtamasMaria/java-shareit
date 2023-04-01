package ru.practicum.shareit.item;

import java.util.Collection;

public interface ItemStorage {
    public Item create(Item item);
    public Item get(int id);
    public Collection<Item> getAllByOwnerId(int owner);
    public Item update(Item item);
    public Collection<Item> search(String text);
}
