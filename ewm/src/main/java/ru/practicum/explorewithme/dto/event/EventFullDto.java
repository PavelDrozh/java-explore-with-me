package ru.practicum.explorewithme.dto.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.dto.category.CategoryResponseDto;
import ru.practicum.explorewithme.dto.user.UserShortResponseDto;
import ru.practicum.explorewithme.model.State;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    Long id;
    String annotation;
    CategoryResponseDto category;
    Long confirmedRequests;
    String createdOn;
    String description;
    String eventDate;
    UserShortResponseDto initiator;
    Location location;
    Boolean paid;
    Long participantLimit;
    String publishedOn;
    Boolean requestModeration;
    State state;
    String title;
    Long views;
}
