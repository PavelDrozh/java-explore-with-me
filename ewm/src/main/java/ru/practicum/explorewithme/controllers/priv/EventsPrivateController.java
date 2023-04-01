package ru.practicum.explorewithme.controllers.priv;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.service.EventRequestService;
import ru.practicum.explorewithme.service.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventsPrivateController {

    EventService service;
    EventRequestService requestService;

    public static final String EVENT_PATH = "/{eventId}";
    public static final String REQUESTS_PATH = "/requests";

    @GetMapping
    public List<EventShortDto> getAllByUser(@PathVariable Long userId,
                                            @RequestParam(required = false, defaultValue = "0")
                                            @PositiveOrZero
                                            int from,
                                            @RequestParam(required = false, defaultValue = "10")
                                            @Positive
                                            int size) {
        return service.getAllByUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable Long userId, @RequestBody @Valid EventCreateDto dto) {
        return service.create(userId, dto);
    }

    @GetMapping(EVENT_PATH)
    public EventFullDto getById(@PathVariable Long userId, @PathVariable Long eventId) {
        return service.getById(userId, eventId);
    }

    @PatchMapping(EVENT_PATH)
    public EventFullDto update(@PathVariable Long userId, @PathVariable Long eventId,
                               @RequestBody UpdateEventUserRequestDto dto) {
        return service.updateByUser(userId, eventId, dto);
    }

    @GetMapping(EVENT_PATH + REQUESTS_PATH)
    public List<ParticipationRequestDto> getRequests(@PathVariable Long userId, @PathVariable Long eventId) {
        return requestService.getRequestsInUsersEvent(userId, eventId);
    }


    @PatchMapping(EVENT_PATH + REQUESTS_PATH)
    public EventRequestStatusUpdateResult updateRequests(@PathVariable Long userId, @PathVariable Long eventId,
                                                         @RequestBody EventRequestStatusUpdateRequest dto) {
        return requestService.updateRequests(userId, eventId, dto);
    }
}
