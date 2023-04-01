package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto addItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader("X-Sharer-User-Id") int owner) {
        log.info("POST-запрос на добавление новой вещи.");
        return itemService.create(itemDto, owner);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable int id) {
        log.info("GET-запрос на вывод вещи по идентификатору.");
        return itemService.getItemById(id);
    }

    @GetMapping
    Collection<ItemDto> getAllItemsByOwnerId(@RequestHeader("X-Sharer-User-Id") int owner) {
        log.info("GET-запрос на вывод всех вещей по идентификатору владельца.");
        return itemService.getAllByOwnerId(owner);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItemById(@PathVariable int id, @RequestHeader("X-Sharer-User-Id") int owner,
                                  @RequestBody ItemDto itemDto) {
        log.info("PATCH-запрос на обновление вещи.");
        return itemService.update(itemDto, id, owner);
    }

    @GetMapping("/search")
    public Collection<ItemDto> searchItem(@RequestParam String text) {
        log.info("GET-запрос на поиск вещи.");
        return itemService.search(text);
    }

}