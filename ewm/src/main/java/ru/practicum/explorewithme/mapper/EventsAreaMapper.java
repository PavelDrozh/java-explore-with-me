package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaCreateDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaShortResponseDto;
import ru.practicum.explorewithme.model.EventsArea;

import java.util.List;

@Component
public class EventsAreaMapper {

    public EventsArea toEventsArea(EventsAreaCreateDto dto) {
        EventsArea eventsArea = new EventsArea();
        eventsArea.setLat(dto.getLat());
        eventsArea.setLon(dto.getLon());
        eventsArea.setDistance(dto.getDistance());
        eventsArea.setName(dto.getName());
        return eventsArea;
    }

    public EventsAreaResponseDto toEventsAreaResponseDto(EventsArea eventsArea) {
        return EventsAreaResponseDto.builder()
                .id(eventsArea.getId())
                .lat(eventsArea.getLat())
                .lon(eventsArea.getLon())
                .distance(eventsArea.getDistance())
                .name(eventsArea.getName())
                .build();
    }

    public EventsAreaShortResponseDto toEventsAreaShortResponseDto(EventsArea eventsArea, List<EventShortDto> events) {
        return EventsAreaShortResponseDto.builder()
                .id(eventsArea.getId())
                .lat(eventsArea.getLat())
                .lon(eventsArea.getLon())
                .distance(eventsArea.getDistance())
                .name(eventsArea.getName())
                .events(events)
                .build();
    }
}
