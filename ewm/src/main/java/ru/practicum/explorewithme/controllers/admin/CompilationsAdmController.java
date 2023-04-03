package ru.practicum.explorewithme.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CompilationCreateDto;
import ru.practicum.explorewithme.dto.CompilationResponseDto;
import ru.practicum.explorewithme.dto.CompilationUpdateDto;
import ru.practicum.explorewithme.service.CompilationsService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationsAdmController {

    CompilationsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationResponseDto create(@RequestBody @Valid CompilationCreateDto dto) {
        return service.create(dto);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long compId) {
        service.delete(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationResponseDto update(@PathVariable Long compId, @RequestBody CompilationUpdateDto dto) {
        return service.update(compId, dto);
    }
}
