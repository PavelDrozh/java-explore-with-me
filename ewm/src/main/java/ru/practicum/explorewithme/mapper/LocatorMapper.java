package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.locator.LocatorCreateDto;
import ru.practicum.explorewithme.dto.locator.LocatorResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorShortResponseDto;
import ru.practicum.explorewithme.model.Locator;

import java.util.List;

@Component
public class LocatorMapper {

    public Locator toLocator(LocatorCreateDto dto) {
        Locator locator = new Locator();
        locator.setLat(dto.getLat());
        locator.setLon(dto.getLon());
        locator.setDistance(dto.getDistance());
        locator.setName(dto.getName());
        return locator;
    }

    public LocatorResponseDto toLocatorResponseDto(Locator locator) {
        return LocatorResponseDto.builder()
                .id(locator.getId())
                .lat(locator.getLat())
                .lon(locator.getLon())
                .distance(locator.getDistance())
                .name(locator.getName())
                .build();
    }

    public LocatorShortResponseDto toLocatorShortResponseDto(Locator locator, List<EventShortDto> events) {
        return LocatorShortResponseDto.builder()
                .id(locator.getId())
                .lat(locator.getLat())
                .lon(locator.getLon())
                .distance(locator.getDistance())
                .name(locator.getName())
                .events(events)
                .build();
    }
}
