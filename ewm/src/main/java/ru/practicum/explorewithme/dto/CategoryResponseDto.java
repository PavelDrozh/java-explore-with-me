package ru.practicum.explorewithme.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
    Long id;
    String name;
}
