package ru.practicum.explorewithme.controllers.pub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.CompilationResponseDto;
import ru.practicum.explorewithme.service.CompilationsService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationPubController {

    CompilationsService service;

    @GetMapping
    public List<CompilationResponseDto> getAllByPinned(@RequestParam(required = false, defaultValue = "FALSE")
                                                           Boolean pinned,
                                                       @RequestParam(required = false, defaultValue = "0")
                                                            int from,
                                                       @RequestParam(required = false, defaultValue = "10")
                                                           int size) {
        return service.getAllByPinned(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationResponseDto getById(@PathVariable Long compId) {
        return service.getById(compId);
    }
}
