package ru.practicum.shareit.user;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class User {
    private int id;
    private String name;
    @Email
    @NotNull
    private String email;

}