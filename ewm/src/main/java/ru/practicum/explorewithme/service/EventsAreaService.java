package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.events_area.EventsAreaCreateDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaShortResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaUpdateDto;

import java.util.List;

public interface EventsAreaService {

    List<EventsAreaResponseDto> getAll(int from, int size, String name);

    EventsAreaShortResponseDto getById(Long id);

    EventsAreaShortResponseDto getByLocationInEventsArea(Float pointLat, Float pointLon);

    EventsAreaResponseDto create(EventsAreaCreateDto dto);

    void remove(Long id);

    EventsAreaResponseDto update(Long id, EventsAreaUpdateDto dto);

    EventsAreaShortResponseDto getByIdForUser(Long userId, Long locatorId);

    EventsAreaShortResponseDto getByLocationForUser(Long userId, Float pointLat, Float pointLon);

    EventsAreaShortResponseDto getPubById(Long locatorId);

    EventsAreaShortResponseDto getPubByLocation(Float pointLat, Float pointLon);
}
