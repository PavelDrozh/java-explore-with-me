package ru.practicum.explorewithme.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
    @Positive
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
