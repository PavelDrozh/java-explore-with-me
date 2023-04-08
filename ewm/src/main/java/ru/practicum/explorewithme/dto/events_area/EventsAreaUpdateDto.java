package ru.practicum.explorewithme.dto.events_area;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventsAreaUpdateDto {

    Float lat;
    Float lon;
    Float distance;
    String name;
}
