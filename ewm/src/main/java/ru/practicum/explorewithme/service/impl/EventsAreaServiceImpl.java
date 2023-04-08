package ru.practicum.explorewithme.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaCreateDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaShortResponseDto;
import ru.practicum.explorewithme.dto.events_area.EventsAreaUpdateDto;
import ru.practicum.explorewithme.exceptions.EventsAreaNotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.EventsAreaMapper;
import ru.practicum.explorewithme.model.EventsArea;
import ru.practicum.explorewithme.model.MainPage;
import ru.practicum.explorewithme.model.State;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.EventsAreaRepository;
import ru.practicum.explorewithme.service.EventsAreaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventsAreaServiceImpl implements EventsAreaService {

    EventsAreaRepository repository;
    EventsAreaMapper mapper;
    EventRepository eventRepository;
    EventMapper eventMapper;

    @Transactional(readOnly = true)
    @Override
    public List<EventsAreaResponseDto> getAll(int from, int size, String name) {
        MainPage page = new MainPage(from, size, Sort.by("name"));
        List<EventsArea> founded;
        if (name.isBlank()) {
            founded = repository.findAll(page).toList();
        } else {
            founded = repository.findAllByName(name, page);
        }
        return founded.stream()
                .map(mapper::toEventsAreaResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventsAreaShortResponseDto getById(Long id) {
        EventsArea founded = getEventsAreaById(id);
        List<EventShortDto> events = getEventsForAdmin(founded);
        return mapper.toEventsAreaShortResponseDto(founded, events);
    }

    private EventsArea getEventsAreaById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EventsAreaNotFoundException(String
                        .format("Локация для проведения мероприятий с id = %d не найдена", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public EventsAreaShortResponseDto getByLocationInEventsArea(Float lat, Float lon) {
        EventsArea founded = getEventsAreaByLoc(lat, lon);
        List<EventShortDto> events = getEventsForAdmin(founded);
        return mapper.toEventsAreaShortResponseDto(founded, events);
    }

    private List<EventShortDto> getEventsForAdmin(EventsArea founded) {
        return eventRepository
                .findAllByLocator(founded.getLat(), founded.getLon(), founded.getDistance())
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    private EventsArea getEventsAreaByLoc(Float pointLat, Float pointLon) {
        return repository.findByLatAndLonInDistance(pointLat, pointLon)
                .orElseThrow(() -> new EventsAreaNotFoundException(String
                        .format("Локация для проведения мероприятий с координатами (%f, %f) не найдена", pointLat, pointLon)));
    }

    @Transactional
    @Override
    public EventsAreaResponseDto create(EventsAreaCreateDto dto) {
        EventsArea locator = mapper.toEventsArea(dto);
        EventsArea saved = repository.save(locator);
        return mapper.toEventsAreaResponseDto(saved);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        getEventsAreaById(id);
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public EventsAreaResponseDto update(Long id, EventsAreaUpdateDto dto) {
        EventsArea founded = getEventsAreaById(id);
        if (dto.getLat() != null) {
            founded.setLat(dto.getLat());
        }
        if (dto.getLon() != null) {
            founded.setLon(dto.getLon());
        }
        if (dto.getDistance() != null) {
            founded.setDistance(dto.getDistance());
        }
        if (dto.getName() != null) {
            founded.setName(dto.getName());
        }
        EventsArea saved = repository.save(founded);
        return mapper.toEventsAreaResponseDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public EventsAreaShortResponseDto getByIdForUser(Long userId, Long locatorId) {
        EventsArea founded = getEventsAreaById(locatorId);
        List<EventShortDto> events = getLocatorEvents(userId, founded);
        return mapper.toEventsAreaShortResponseDto(founded, events);
    }

    private List<EventShortDto> getLocatorEvents(Long userId, EventsArea founded) {
        return eventRepository
                .findAllByLocatorAndUser(founded.getLat(), founded.getLon(), founded.getDistance(), userId)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventsAreaShortResponseDto getByLocationForUser(Long userId, Float lat, Float lon) {
        EventsArea founded = getEventsAreaByLoc(lat, lon);
        List<EventShortDto> events = getLocatorEvents(userId, founded);
        return mapper.toEventsAreaShortResponseDto(founded, events);
    }

    @Transactional(readOnly = true)
    @Override
    public EventsAreaShortResponseDto getPubById(Long locatorId) {
        EventsArea founded = getEventsAreaById(locatorId);
        List<EventShortDto> events = getPubEventsByLoc(founded);
        return mapper.toEventsAreaShortResponseDto(founded, events);
    }

    private List<EventShortDto> getPubEventsByLoc(EventsArea founded) {
        return eventRepository
                .findAllPubByLocator(founded.getLat(), founded.getLon(), founded.getDistance(), State.PUBLISHED)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EventsAreaShortResponseDto getPubByLocation(Float lat, Float lon) {
        EventsArea founded = getEventsAreaByLoc(lat, lon);
        List<EventShortDto> events = getPubEventsByLoc(founded);
        return mapper.toEventsAreaShortResponseDto(founded, events);
    }
}
