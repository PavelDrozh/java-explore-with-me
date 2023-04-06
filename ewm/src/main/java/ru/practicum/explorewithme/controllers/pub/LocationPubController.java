package ru.practicum.explorewithme.controllers.pub;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.locator.LocatorResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorShortResponseDto;
import ru.practicum.explorewithme.service.LocatorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationPubController {

    private static final String LOCATOR_PATH = "/{locatorId}";

    LocatorService service;

    @GetMapping
    public List<LocatorResponseDto> getAll(@RequestParam(required = false, defaultValue = "0") int from,
                                           @RequestParam(required = false, defaultValue = "10") int size,
                                           @RequestParam(required = false, defaultValue = "") String name) {
        return service.getAll(from, size, name);
    }

    @GetMapping(LOCATOR_PATH)
    public LocatorShortResponseDto getById(@PathVariable Long locatorId) {
        return service.getPubById(locatorId);
    }

    @GetMapping("/locator")
    public LocatorShortResponseDto getByLocation(@RequestParam Float lat,
                                                 @RequestParam Float lon) {
        return service.getPubByLocation(lat, lon);
    }

}
