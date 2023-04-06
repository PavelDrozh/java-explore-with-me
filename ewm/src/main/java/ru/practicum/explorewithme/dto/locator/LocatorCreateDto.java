package ru.practicum.explorewithme.dto.locator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocatorCreateDto {
    @NotNull
    Float lat;
    @NotNull
    Float lon;
    @NotNull
    Float distance;
    @NotBlank
    String name;
}
