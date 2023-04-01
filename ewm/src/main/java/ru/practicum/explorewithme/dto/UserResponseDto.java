package ru.practicum.explorewithme.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    Long id;
    String name;
    String email;
}
