package ru.practicum.explorewithme.dto.locator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocatorUpdateDto {

    Float lat;
    Float lon;
    Float distance;
    String name;
}
