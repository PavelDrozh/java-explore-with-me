package ru.practicum.explorewithme.service;

import ru.practicum.explorewithme.StatsCreateDto;
import ru.practicum.explorewithme.StatsResponseDto;

import java.util.List;

public interface StatsInfoService {

    List<StatsResponseDto> getStatistics(String start, String end, List<String> uris, Boolean unique);

    StatsResponseDto createHit(StatsCreateDto dto);
}
