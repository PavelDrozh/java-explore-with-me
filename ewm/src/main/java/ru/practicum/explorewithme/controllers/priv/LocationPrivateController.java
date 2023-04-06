package ru.practicum.explorewithme.controllers.priv;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.locator.LocatorResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorShortResponseDto;
import ru.practicum.explorewithme.service.LocatorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/location")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationPrivateController {

    private static final String LOCATOR_PATH = "/{locatorId}";

    LocatorService service;

    @GetMapping
    public List<LocatorResponseDto> getAll(@RequestParam(required = false, defaultValue = "0") int from,
                                           @RequestParam(required = false, defaultValue = "10") int size,
                                           @RequestParam(required = false, defaultValue = "") String name) {
        return service.getAll(from, size, name);
    }

    @GetMapping(LOCATOR_PATH)
    public LocatorShortResponseDto getById(@PathVariable Long userId, @PathVariable Long locatorId) {
        return service.getByIdForUser(userId, locatorId);
    }

    @GetMapping("locator")
    public LocatorShortResponseDto getByLocation(@PathVariable Long userId,
                                                 @RequestParam Float lat,
                                                 @RequestParam Float lon) {
        return service.getByLocationForUser(userId, lat, lon);
    }
}
