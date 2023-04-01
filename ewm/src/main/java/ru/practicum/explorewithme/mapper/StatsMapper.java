package ru.practicum.explorewithme.mapper;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.StatsCreateDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatsMapper {

    static String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public StatsCreateDto toStatsCreateDto(HttpServletRequest request) {
        return StatsCreateDto.builder()
                .app("ewm-service")
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATTERN)))
                .build();
    }
}
