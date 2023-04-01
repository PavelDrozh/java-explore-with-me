package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.CompilationCreateDto;
import ru.practicum.explorewithme.dto.CompilationResponseDto;
import ru.practicum.explorewithme.model.Compilation;

@Component
public class CompilationMapper {

    public Compilation toCompilation(CompilationCreateDto dto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(dto.getTitle());
        compilation.setPinned(dto.getPinned());
        return compilation;
    }

    public CompilationResponseDto toCompilationResponseDto(Compilation compilation) {
        return CompilationResponseDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .build();
    }
}
