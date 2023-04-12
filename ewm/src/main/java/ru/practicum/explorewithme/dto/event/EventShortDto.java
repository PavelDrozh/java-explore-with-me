package ru.practicum.explorewithme.dto.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.dto.category.CategoryResponseDto;
import ru.practicum.explorewithme.dto.user.UserShortResponseDto;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventShortDto {
    Long id;
    String annotation;
    CategoryResponseDto category;
    Long confirmedRequests;
    String eventDate;
    UserShortResponseDto initiator;
    Boolean paid;
    String title;
    Long views;
}
