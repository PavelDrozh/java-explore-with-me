package ru.practicum.explorewithme.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

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
