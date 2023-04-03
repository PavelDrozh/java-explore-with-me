package ru.practicum.explorewithme.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
public class Location {
    @NotNull
    Float lat;
    @NotNull
    Float lon;
}
