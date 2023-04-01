package ru.practicum.explorewithme.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsersCreateDto {
    @NotBlank
    String name;

    @Email
    String email;
}
