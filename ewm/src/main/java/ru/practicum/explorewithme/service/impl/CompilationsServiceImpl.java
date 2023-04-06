package ru.practicum.explorewithme.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.dto.compilation.CompilationCreateDto;
import ru.practicum.explorewithme.dto.compilation.CompilationResponseDto;
import ru.practicum.explorewithme.dto.compilation.CompilationUpdateDto;
import ru.practicum.explorewithme.dto.event.EventShortDto;
import ru.practicum.explorewithme.exceptions.CompilationNotFoundException;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;
import ru.practicum.explorewithme.mapper.CompilationMapper;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.model.Compilation;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.MainPage;
import ru.practicum.explorewithme.repository.CompilationRepository;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.CompilationsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompilationsServiceImpl implements CompilationsService {

    CompilationRepository repository;
    EventRepository eventRepository;
    CompilationMapper mapper;
    EventMapper eventMapper;

    @Override
    public CompilationResponseDto create(CompilationCreateDto dto) {
        Compilation forSave = mapper.toCompilation(dto);
        List<Event> events = getEvents(dto.getEvents());
        forSave.setEvents(events);
        Compilation saved = repository.save(forSave);
        return getCompilationResponse(saved);
    }

    @Override
    public void delete(Long compId) {
        getCompilation(compId);
        repository.deleteById(compId);
    }

    @Override
    public CompilationResponseDto update(Long compId, CompilationUpdateDto dto) {
        Compilation forUpdate = getCompilation(compId);
        if (dto.getPinned() != null) {
            forUpdate.setPinned(dto.getPinned());
        }
        if (dto.getTitle() != null) {
            forUpdate.setTitle(dto.getTitle());
        }
        if (dto.getEvents() != null) {
            List<Event> events = getEvents(dto.getEvents());
            forUpdate.setEvents(events);
        }
        Compilation saved = repository.save(forUpdate);
        return getCompilationResponse(saved);
    }

    private CompilationResponseDto getCompilationResponse(Compilation compilation) {
        CompilationResponseDto responseDto = mapper.toCompilationResponseDto(compilation);
        List<EventShortDto> eventShortDtos = compilation.getEvents().stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
        responseDto.setEvents(eventShortDtos);
        return responseDto;
    }

    @Override
    public List<CompilationResponseDto> getAllByPinned(Boolean pinned, int from, int size) {
        MainPage page = new MainPage(from, size, Sort.unsorted());
        return repository.findAllByPinned(pinned, page).stream()
                .map(this::getCompilationResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationResponseDto getById(Long compId) {
        Compilation founded = getCompilation(compId);
        return getCompilationResponse(founded);
    }

    private Compilation getCompilation(Long compId) {
        return repository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(String.format("Подборка с id = %s не найдена",
                        compId)));
    }

    private List<Event> getEvents(List<Long> eventIds) {
        List<Event> events = eventRepository.findAllByIdIn(eventIds);
        if (events.size() != eventIds.size()) {
            throw new EventNotFoundException(String.format("Из событий с id {%s} не найдено не найдено %d",
                    eventIds, eventIds.size() - events.size()));
        }
        return events;
    }
}
