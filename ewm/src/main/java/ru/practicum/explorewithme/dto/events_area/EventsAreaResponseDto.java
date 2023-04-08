package ru.practicum.explorewithme.dto.events_area;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventsAreaResponseDto {
    Long id;
    Float lat;
    Float lon;
    Float distance;
    String name;
}
