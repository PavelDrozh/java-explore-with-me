package ru.practicum.explorewithme.dto.compilation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationUpdateDto {
    List<Long> events;
    Boolean pinned;
    String title;
}
