package ru.practicum.explorewithme.controllers.pub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.events_area.EventsAreaResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaShortResponseDto;
import ru.practicum.explorewithme.service.EventsAreaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/eventsArea")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationPubController {

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
        return service.getPubById(eventsAreaId);
    }

    @GetMapping("/location")
    public EventsAreaShortResponseDto getByLocation(@RequestParam Float pointLat,
                                                    @RequestParam Float pointLon) {
        return service.getPubByLocation(pointLat, pointLon);
    }

}
