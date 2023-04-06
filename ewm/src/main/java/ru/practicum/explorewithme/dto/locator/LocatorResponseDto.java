package ru.practicum.explorewithme.dto.locator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocatorResponseDto {
    Long id;
    Float lat;
    Float lon;
    Float distance;
    String name;
}
