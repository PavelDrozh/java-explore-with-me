package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.*;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.State;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {

    List<EventShortDto> getAllByUser(Long userId, int from, int size);

    EventFullDto create(Long userId, EventCreateDto dto);

    EventFullDto getById(Long userId, Long eventId);

    EventFullDto updateByUser(Long userId, Long eventId, UpdateEventUserRequestDto dto);

    List<EventFullDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                     String rangeStart, String rangeEnd, int from, int size);

    EventFullDto updateByAdmin(Long eventId, UpdateEventAdminRequestDto dto);

    Event getEventById(Long eventId);

    List<EventShortDto> getAllByPublicQuery(String text, List<Long> categories, Boolean paid, String rangeStart,
                                            String rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                            HttpServletRequest request);

    EventFullDto getByIdPublic(Long eventId, HttpServletRequest request);

}
