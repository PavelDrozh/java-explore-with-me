package ru.practicum.explorewithme.dto.event;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventCreateDto {

    @Size(min = 20, max = 2000)
    @NotBlank
    String annotation;
    @Positive
    Long category;
    @Size(min = 20, max = 7000)
    @NotBlank
    String description;
    @NotBlank
    String eventDate;
    @NotNull
    Boolean paid;
    @PositiveOrZero
    Long participantLimit;
    @NotNull
    Boolean requestModeration;
    @Size(min = 3, max = 120)
    @NotBlank
    String title;
    @NotNull
    @Valid
    Location location;
}
