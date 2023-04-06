package ru.practicum.explorewithme.dto.compilation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompilationCreateDto {

    @NotNull
    List<Long> events;

    @NotNull
    Boolean pinned;

    @NotBlank
    String title;
}
