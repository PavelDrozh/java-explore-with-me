package ru.practicum.explorewithme.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.StatsCreateDto;
import ru.practicum.explorewithme.StatsResponseDto;
import ru.practicum.explorewithme.service.StatsInfoService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsController {

    StatsInfoService service;

    @GetMapping("/stats")
    public List<StatsResponseDto> getStatistic(@RequestParam String start, @RequestParam String end,
                                               @RequestParam (required = false, defaultValue = "") List<String> uris,
                                               @RequestParam (required = false, defaultValue = "false")
                                               Boolean unique) {
        return service.getStatistics(start, end, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public StatsResponseDto createHit(@RequestBody @Valid StatsCreateDto dto) {
        return service.createHit(dto);
    }

}
