package ru.practicum.explorewithme.dto.locator;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.dto.event.EventShortDto;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LocatorShortResponseDto {
    Long id;
    Float lat;
    Float lon;
    Float distance;
    String name;
    List<EventShortDto> events;
}
