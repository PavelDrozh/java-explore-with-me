package ru.practicum.explorewithme.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.StatsCreateDto;
import ru.practicum.explorewithme.StatsResponseDto;
import ru.practicum.explorewithme.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EndpointHitMapper {

    public EndpointHit toEndpointHit(StatsCreateDto dto) {
        EndpointHit result = new EndpointHit();
        result.setApp(dto.getApp());
        result.setIp(dto.getIp());
        result.setUri(dto.getUri());
        result.setTimestamp(LocalDateTime
                .parse(dto.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return result;
    }

    public StatsResponseDto toStatsResponseDto(EndpointHit data) {
        return new StatsResponseDto(data.getApp(), data.getIp(), 1L);
    }
}
