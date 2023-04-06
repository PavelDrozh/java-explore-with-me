package ru.practicum.explorewithme.dto.event;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.dto.enums.UserStateAction;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventUserRequestDto {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    UserStateAction stateAction;
    String title;
}
