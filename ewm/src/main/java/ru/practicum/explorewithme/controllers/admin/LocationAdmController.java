package ru.practicum.explorewithme.controllers.admin;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.locator.LocatorCreateDto;
import ru.practicum.explorewithme.dto.locator.LocatorResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorShortResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorUpdateDto;
import ru.practicum.explorewithme.service.LocatorService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/location")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationAdmController {

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
        return service.getById(locatorId);
    }

    @GetMapping("/locator")
    public LocatorShortResponseDto getByLocation(@RequestParam Float lat,
                                                 @RequestParam Float lon) {
        return service.getByLocation(lat, lon);
    }

    @PostMapping
    public LocatorResponseDto create(@RequestBody @Valid LocatorCreateDto dto) {
        return service.create(dto);
    }

    @PatchMapping(LOCATOR_PATH)
    public LocatorResponseDto update(@PathVariable Long locatorId, @RequestBody @NotNull LocatorUpdateDto dto) {
        return service.update(locatorId, dto);
    }

    @DeleteMapping(LOCATOR_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long locatorId) {
        service.remove(locatorId);
    }
}
