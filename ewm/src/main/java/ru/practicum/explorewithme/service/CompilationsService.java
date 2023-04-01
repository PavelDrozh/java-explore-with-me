package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.CompilationCreateDto;
import ru.practicum.explorewithme.dto.CompilationResponseDto;
import ru.practicum.explorewithme.dto.CompilationUpdateDto;

import java.util.List;

public interface CompilationsService {

    CompilationResponseDto create(CompilationCreateDto dto);

    void delete(Long compId);

    CompilationResponseDto update(Long compId, CompilationUpdateDto dto);

    List<CompilationResponseDto> getAllByPinned(Boolean pinned, int from, int size);

    CompilationResponseDto getById(Long compId);
}
