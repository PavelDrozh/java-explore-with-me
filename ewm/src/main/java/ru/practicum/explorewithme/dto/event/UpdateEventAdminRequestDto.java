package ru.practicum.explorewithme.dto.event;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.dto.enums.AdminStateAction;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventAdminRequestDto {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Long participantLimit;
    Boolean requestModeration;
    AdminStateAction stateAction;
    String title;
}
