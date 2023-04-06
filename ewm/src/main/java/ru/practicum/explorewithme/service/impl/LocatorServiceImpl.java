package ru.practicum.explorewithme.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.dto.locator.LocatorCreateDto;
import ru.practicum.explorewithme.dto.locator.LocatorResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorShortResponseDto;
import ru.practicum.explorewithme.dto.locator.LocatorUpdateDto;
import ru.practicum.explorewithme.exceptions.LocatorNotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.LocatorMapper;
import ru.practicum.explorewithme.model.Locator;
import ru.practicum.explorewithme.model.MainPage;
import ru.practicum.explorewithme.model.State;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.LocatorRepository;
import ru.practicum.explorewithme.service.LocatorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocatorServiceImpl implements LocatorService {

    LocatorRepository repository;
    LocatorMapper mapper;
    EventRepository eventRepository;
    EventMapper eventMapper;

    @Transactional(readOnly = true)
    @Override
    public List<LocatorResponseDto> getAll(int from, int size, String name) {
        MainPage page = new MainPage(from, size, Sort.by("name"));
        List<Locator> founded;
        if (name.isBlank()) {
            founded = repository.findAll(page).toList();
        } else {
            founded = repository.findAllByName(name, page);
        }
        return founded.stream()
                .map(mapper::toLocatorResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public LocatorShortResponseDto getById(Long id) {
        Locator founded = getLocatorById(id);
        List<EventShortDto> events = getEventsForAdmin(founded);
        return mapper.toLocatorShortResponseDto(founded, events);
    }

    private Locator getLocatorById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new LocatorNotFoundException(String
                        .format("Локация для проведения мероприятий с id = %d не найдена", id)));
    }

    @Transactional(readOnly = true)
    @Override
    public LocatorShortResponseDto getByLocation(Float lat, Float lon) {
        Locator founded = getLocatorByLoc(lat, lon);
        List<EventShortDto> events = getEventsForAdmin(founded);
        return mapper.toLocatorShortResponseDto(founded, events);
    }

    private List<EventShortDto> getEventsForAdmin(Locator founded) {
        return eventRepository
                .findAllByLocator(founded.getLat(), founded.getLon(), founded.getDistance())
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    private Locator getLocatorByLoc(Float lat, Float lon) {
        return repository.findByLatAndLonInDistance(lat, lon)
                .orElseThrow(() -> new LocatorNotFoundException(String
                        .format("Локация для проведения мероприятий с координатами (%f, %f) не найдена", lat, lon)));
    }

    @Transactional
    @Override
    public LocatorResponseDto create(LocatorCreateDto dto) {
        Locator locator = mapper.toLocator(dto);
        Locator saved = repository.save(locator);
        return mapper.toLocatorResponseDto(saved);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        getLocatorById(id);
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public LocatorResponseDto update(Long id, LocatorUpdateDto dto) {
        Locator founded = getLocatorById(id);
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
        Locator saved = repository.save(founded);
        return mapper.toLocatorResponseDto(saved);
    }

    @Transactional(readOnly = true)
    @Override
    public LocatorShortResponseDto getByIdForUser(Long userId, Long locatorId) {
        Locator founded = getLocatorById(locatorId);
        List<EventShortDto> events = getLocatorEvents(userId, founded);
        return mapper.toLocatorShortResponseDto(founded, events);
    }

    private List<EventShortDto> getLocatorEvents(Long userId, Locator founded) {
        return eventRepository
                .findAllByLocatorAndUser(founded.getLat(), founded.getLon(), founded.getDistance(), userId)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public LocatorShortResponseDto getByLocationForUser(Long userId, Float lat, Float lon) {
        Locator founded = getLocatorByLoc(lat, lon);
        List<EventShortDto> events = getLocatorEvents(userId, founded);
        return mapper.toLocatorShortResponseDto(founded, events);
    }

    @Transactional(readOnly = true)
    @Override
    public LocatorShortResponseDto getPubById(Long locatorId) {
        Locator founded = getLocatorById(locatorId);
        List<EventShortDto> events = getPubEventsByLoc(founded);
        return mapper.toLocatorShortResponseDto(founded, events);
    }

    private List<EventShortDto> getPubEventsByLoc(Locator founded) {
        return eventRepository
                .findAllPubByLocator(founded.getLat(), founded.getLon(), founded.getDistance(), State.PUBLISHED)
                .stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public LocatorShortResponseDto getPubByLocation(Float lat, Float lon) {
        Locator founded = getLocatorByLoc(lat, lon);
        List<EventShortDto> events = getPubEventsByLoc(founded);
        return mapper.toLocatorShortResponseDto(founded, events);
    }
}
