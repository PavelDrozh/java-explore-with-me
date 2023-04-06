package ru.practicum.explorewithme.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateRequest;
import ru.practicum.explorewithme.dto.request.EventRequestStatusUpdateResult;
import ru.practicum.explorewithme.dto.request.EventRequestUpdateStatus;
import ru.practicum.explorewithme.dto.request.ParticipationRequestDto;
import ru.practicum.explorewithme.exceptions.ConflictException;
import ru.practicum.explorewithme.exceptions.EventRequestNotFoundException;
import ru.practicum.explorewithme.mapper.EventRequestMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.EventRequest;
import ru.practicum.explorewithme.model.RequestStatus;
import ru.practicum.explorewithme.model.State;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.repository.EventRequestRepository;
import ru.practicum.explorewithme.service.EventRequestService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventRequestServiceImpl implements EventRequestService {

    EventRequestRepository repository;
    EventRepository eventRepository;
    EventRequestMapper mapper;
    EventService eventService;
    UserService userService;

    @Override
    public List<ParticipationRequestDto> getRequestsByUserId(Long userId) {
        return repository.findAllByRequesterId(userId)
                .stream()
                .map(mapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        if (repository.findByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ConflictException(String.format("Заявка на событие с id = %d пользователем с id = %d уже подана",
                    eventId, userId));
        }
        Event event = eventService.getEventById(eventId);
        Long limit = event.getParticipantLimit();
        Long confirmed = event.getConfirmedRequests();
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException(String.format("Событие с id = %d не опубликовано", eventId));
        }
        if (event.getInitiator().getId().equals(userId)) {
            throw new ConflictException(String.format("Событие с id = %d создана пользователем с id = %d, " +
                            "инициатор не может подавать заявку на участие", eventId, userId));
        }
        checkRequestLimit(eventId, event, limit, confirmed);

        EventRequest forSave = mapper.getEventRequest();
        forSave.setRequester(userService.getUserById(userId));
        forSave.setEvent(event);
        if (event.getRequestModeration().equals(true)) {
            forSave.setStatus(RequestStatus.PENDING);
        } else {
            forSave.setStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(confirmed + 1);
            eventRepository.save(event);
        }
        EventRequest saved = repository.save(forSave);
        return mapper.toParticipationRequestDto(saved);
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        EventRequest request = repository.findById(requestId)
                .orElseThrow(() -> new EventRequestNotFoundException(String
                        .format("Запрос с id = %d не найден", requestId)));
        if (!request.getRequester().getId().equals(userId)) {
            throw new EventRequestNotFoundException(String
                    .format("Запрос с id = %d, для пользователя с id = %d не найден", requestId, userId));
        }
        request.setStatus(RequestStatus.CANCELED);
        EventRequest updated = repository.save(request);
        return mapper.toParticipationRequestDto(updated);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsInUsersEvent(Long userId, Long eventId) {
        Event event = eventService.getEventById(eventId);
        checkEventInitiator(userId, event);
        return repository.findAllByEventId(eventId)
                .stream()
                .map(mapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult updateRequests(Long userId, Long eventId,
                                                         EventRequestStatusUpdateRequest dto) {
        Event event = eventService.getEventById(eventId);
        checkEventInitiator(userId, event);
        Long limit = event.getParticipantLimit();
        Long confirmed = event.getConfirmedRequests();
        if (limit == 0 || event.getRequestModeration().equals(false)) {
            throw new ConflictException(String.format("Для собятия с id = %d подтверждение заявок не требуется",
                    eventId));
        }
        checkRequestLimit(eventId, event, limit, confirmed);
        List<EventRequest> requestsForUpdate = repository.findAllByEventIdAndIdIn(eventId, dto.getRequestIds());
        if (dto.getStatus().equals(EventRequestUpdateStatus.REJECTED)) {
            requestsForUpdate.forEach(req -> {
                checkRequestStatus(req);
                req.setStatus(RequestStatus.REJECTED);
            });
        } else {
            for (EventRequest req : requestsForUpdate) {
                checkRequestStatus(req);
                if (limit > confirmed) {
                    req.setStatus(RequestStatus.CONFIRMED);
                    confirmed++;
                } else {
                    req.setStatus(RequestStatus.REJECTED);
                }
            }
        }
        event.setConfirmedRequests(confirmed);
        eventRepository.save(event);
        List<EventRequest> saved = repository.saveAll(requestsForUpdate);
        return mapper.toEventRequestStatusUpdateResult(saved);
    }

    private void checkRequestLimit(Long eventId, Event event, Long limit, Long confirmed) {
        if (limit.equals(confirmed) && !event.getParticipantLimit().equals(0L)) {
            throw new ConflictException(String.format("На событие с id = %d создано максимальное колчество " +
                    "заявок на участие", eventId));
        }
    }

    private void checkRequestStatus(EventRequest req) {
        if (!req.getStatus().equals(RequestStatus.PENDING)) {
            throw new EventRequestNotFoundException(String.format("Запрос с id = %d имеет статус %s," +
                            " изменение невозможно", req.getId(), req.getStatus().name()));
        }
    }

    private void checkEventInitiator(Long userId, Event event) {
        if (!event.getInitiator().getId().equals(userId)) {
            throw new EventRequestNotFoundException(String.format("Получить информацию о запросе на участие в событии" +
                    "с id = %d может только инициатор события", event.getId()));
        }
    }
}
