package ru.practicum.explorewithme.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    Long id;
    String name;
    String email;
}
