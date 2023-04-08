package ru.practicum.explorewithme.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.events_area.EventsAreaCreateDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaShortResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaUpdateDto;
import ru.practicum.explorewithme.service.EventsAreaService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/eventsArea")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationAdmController {

    private static final String LOCATOR_PATH = "/{eventsAreaId}";

    EventsAreaService service;

    @GetMapping
    public List<EventsAreaResponseDto> getAll(@RequestParam(required = false, defaultValue = "0") int from,
                                              @RequestParam(required = false, defaultValue = "10") int size,
                                              @RequestParam(required = false, defaultValue = "") String name) {
        return service.getAll(from, size, name);
    }

    @GetMapping(LOCATOR_PATH)
    public EventsAreaShortResponseDto getById(@PathVariable Long eventsAreaId) {
        return service.getById(eventsAreaId);
    }

    @GetMapping("/location")
    public EventsAreaShortResponseDto getByLocation(@RequestParam Float pointLat,
                                                    @RequestParam Float pointLon) {
        return service.getByLocationInEventsArea(pointLat, pointLon);
    }

    @PostMapping
    public EventsAreaResponseDto create(@RequestBody @Valid EventsAreaCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping(LOCATOR_PATH)
    public EventsAreaResponseDto update(@PathVariable Long eventsAreaId, @RequestBody @NotNull EventsAreaUpdateDto dto) {
        return service.update(eventsAreaId, dto);
    }

    @DeleteMapping(LOCATOR_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long eventsAreaId) {
        service.remove(eventsAreaId);
    }
}
