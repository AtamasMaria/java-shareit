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
        log.info("POST-запрос на добавление новой вещи");
        return itemService.add(itemDto, owner);
    }

    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable int id) {
        log.info("GET-запрос на вывод вещи по id");
        return itemService.getItemById(id);
    }

    @GetMapping
    Collection<ItemDto> getAllItems(@RequestHeader("X-Sharer-User-Id") int owner) {
        log.info("GET-запрос на вывод всех вещей");
        return itemService.getAllToOwner(owner);
    }

    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable int id, @RequestHeader("X-Sharer-User-Id") int owner,
                              @RequestBody ItemDto itemDto) {
        log.info("PATCH-запрос на обновление вещи по id");
        return itemService.updateById(itemDto, id, owner);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestParam String text) {
        log.info("GET-запрос на поск {}", text);
        return itemService.search(text);
    }

}