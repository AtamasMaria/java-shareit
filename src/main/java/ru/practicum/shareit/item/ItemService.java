package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    public ItemDto create(ItemDto itemDto, int owner) {
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(owner);
        userService.checkUserId(item.getOwner());
        return ItemMapper.toItemDto(itemStorage.create(item));
    }
    public ItemDto getItemById(int id) {
        return ItemMapper.toItemDto(itemStorage.get(id));
    }
    public Collection<ItemDto> getAllByOwnerId(int ownerId) {
        return itemStorage.getAllByOwnerId(ownerId).stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }
    public ItemDto update(ItemDto newItem, int id, int owner) {
        Item item = itemStorage.get(id);
        if (item.getOwner() != owner)
            throw new NotFoundException("Invalid user");
        if (newItem.getName() != null)
            item.setName(newItem.getName());
        if (newItem.getAvailable() != null)
            item.setAvailable(newItem.getAvailable());
        if ((newItem.getDescription() != null))
            item.setDescription(newItem.getDescription());
        return ItemMapper.toItemDto(itemStorage.update(item));
    }
    public Collection<ItemDto> search(String text) {
        return itemStorage.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}