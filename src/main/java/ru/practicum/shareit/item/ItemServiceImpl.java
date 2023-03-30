package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.UserService;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserService userService;

    @Override
    public ItemDto add(ItemDto itemDto, int owner) {
        Item item = ItemMapper.toItem(itemDto);
        item.setOwner(owner);
        userService.checkUserId(item.getOwner());
        return ItemMapper.toItemDto(itemStorage.add(item));
    }

    @Override
    public ItemDto getItemById(int id) {
        return ItemMapper.toItemDto(itemStorage.getById(id));
    }

    @Override
    public Collection<ItemDto> getAllToOwner(int owner) {
        return itemStorage.getAll(owner).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto updateById(ItemDto newItem, int id, int owner) {
        Item item = itemStorage.getById(id);
        if (item.getOwner() != owner)
            throw new NotFoundException("Пользователь не был найден");
        if (newItem.getName() != null)
            item.setName(newItem.getName());
        if (newItem.getAvailable() != null)
            item.setAvailable(newItem.getAvailable());
        if ((newItem.getDescription() != null))
            item.setDescription(newItem.getDescription());
        return ItemMapper.toItemDto(itemStorage.update(item));
    }

    @Override
    public Collection<ItemDto> search(String text) {
        return itemStorage.search(text).stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
