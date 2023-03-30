package ru.practicum.shareit.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ItemRequest {
    private int id;
    private String description;
    private int requester;
    private LocalDateTime created;
}
