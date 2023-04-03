package ru.practicum.explorewithme.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationResponseDto {
    Long id;
    List<EventShortDto> events;
    Boolean pinned;
    String title;
}
