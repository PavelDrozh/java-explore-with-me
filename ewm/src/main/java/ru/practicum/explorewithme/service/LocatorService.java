package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.dto.locator.LocatorCreateDto;
import ru.practicum.explorewithme.dto.locator.LocatorResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorShortResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorUpdateDto;

import java.util.List;

public interface LocatorService {

    List<LocatorResponseDto> getAll(int from, int size, String name);

    LocatorShortResponseDto getById(Long id);

    LocatorShortResponseDto getByLocation(Float lat, Float lon);

    LocatorResponseDto create(LocatorCreateDto dto);

    void remove(Long id);

    LocatorResponseDto update(Long id, LocatorUpdateDto dto);

    LocatorShortResponseDto getByIdForUser(Long userId, Long locatorId);

    LocatorShortResponseDto getByLocationForUser(Long userId, Float lat, Float lon);

    LocatorShortResponseDto getPubById(Long locatorId);

    LocatorShortResponseDto getPubByLocation(Float lat, Float lon);
}
