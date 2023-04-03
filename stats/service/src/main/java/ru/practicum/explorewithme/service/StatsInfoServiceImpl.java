package ru.practicum.explorewithme.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.StatsCreateDto;
import ru.practicum.explorewithme.StatsResponseDto;
import ru.practicum.explorewithme.exceptions.IncorrectDateTime;
import ru.practicum.explorewithme.mapper.EndpointHitMapper;
import ru.practicum.explorewithme.model.EndpointHit;
import ru.practicum.explorewithme.repository.StatsServiceRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class StatsInfoServiceImpl implements StatsInfoService {

    StatsServiceRepository repository;
    EndpointHitMapper mapper;

    @Override
    public List<StatsResponseDto> getStatistics(String start, String end, List<String> uris, Boolean unique) {
        LocalDateTime startDate = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime endDate = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        checkDates(startDate, endDate);

        List<StatsResponseDto> responseDtos;
        if (uris.isEmpty()) {
            if (unique) {
                responseDtos = repository.findUniqueStatsWithOutUri(startDate, endDate);
            } else {
                responseDtos = repository.findStatsWithOutUri(startDate, endDate);
            }
        } else {
            if (unique) {
                responseDtos = repository.findUniqueStats(startDate, endDate, uris);
            } else {
                responseDtos = repository.findStats(startDate, endDate, uris);
            }
        }
        return responseDtos;
    }

    private void checkDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IncorrectDateTime(String
                    .format("Промежуток времени выборки некорректен, начало = %s, конец = %s", startDate, endDate));
        }
    }

    @Override
    public StatsResponseDto createHit(StatsCreateDto dto) {
        EndpointHit forSave = mapper.toEndpointHit(dto);
        EndpointHit saved = repository.save(forSave);
        return mapper.toStatsResponseDto(saved);
    }


}
