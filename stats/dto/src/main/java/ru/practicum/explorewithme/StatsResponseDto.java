package ru.practicum.explorewithme;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class StatsResponseDto {
    String app;
    String uri;
    Long hits;
}
