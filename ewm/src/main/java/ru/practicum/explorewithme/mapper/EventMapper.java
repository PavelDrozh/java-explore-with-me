package ru.practicum.explorewithme.mapper;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.event.EventCreateDto;
import ru.practicum.explorewithme.dto.event.EventFullDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EventMapper {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    CategoryMapper categoryMapper;
    UserMapper userMapper;

    public Event toEvent(EventCreateDto dto) {
        Event event = new Event();
        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(LocalDateTime.parse(dto.getEventDate(), DateTimeFormatter.ofPattern(PATTERN)));
        event.setPaid(dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit());
        event.setRequestModeration(dto.getRequestModeration());
        event.setTitle(dto.getTitle());
        event.setLocation(dto.getLocation());
        return event;
    }

    public EventFullDto toEventFullDto(Event event) {
        String published;
        if (event.getPublishedOn() == null) {
            published = "null";
        } else {
            published = event.getPublishedOn().toString();
        }
        return EventFullDto.builder()
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(PATTERN)))
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryResponseDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(DateTimeFormatter.ofPattern(PATTERN)))
                .description(event.getDescription())
                .initiator(userMapper.toShortResponseDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(published)
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .eventDate(event.getEventDate().format(DateTimeFormatter.ofPattern(PATTERN)))
                .annotation(event.getAnnotation())
                .category(categoryMapper.toCategoryResponseDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .id(event.getId())
                .initiator(userMapper.toShortResponseDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
}
