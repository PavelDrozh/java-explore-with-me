package ru.practicum.explorewithme.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.StatsClient;
import ru.practicum.explorewithme.dto.enums.AdminStateAction;
import ru.practicum.explorewithme.dto.enums.SortForPubEventsController;
import ru.practicum.explorewithme.dto.enums.UserStateAction;
import ru.practicum.explorewithme.dto.event.*;
import ru.practicum.explorewithme.exceptions.BadRequestException;
import ru.practicum.explorewithme.exceptions.ConflictException;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;
import ru.practicum.explorewithme.mapper.EventMapper;
import ru.practicum.explorewithme.mapper.StatsMapper;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.MainPage;
import ru.practicum.explorewithme.model.QEvent;
import ru.practicum.explorewithme.model.State;
import ru.practicum.explorewithme.repository.EventRepository;
import ru.practicum.explorewithme.service.CategoryService;
import ru.practicum.explorewithme.service.EventService;
import ru.practicum.explorewithme.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EventServiceImpl implements EventService {

    static String PATTERN = "yyyy-MM-dd HH:mm:ss";
    EventRepository repository;
    EventMapper mapper;
    UserService userService;
    CategoryService categoryService;
    StatsClient client;
    StatsMapper statsMapper;

    @Override
    public List<EventShortDto> getAllByUser(Long userId, int from, int size) {
        MainPage page = new MainPage(from, size, Sort.by("eventDate").ascending());
        return repository.findAllByInitiatorId(userId, page).stream()
                .map(mapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto create(Long userId, EventCreateDto dto) {
        Event newEvent = mapper.toEvent(dto);
        if (newEvent.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException(String.format("Дата и время на которые намечено новое событие (%s) " +
                            "не может быть раньше, чем через два часа от текущего момента (%s).",
                    newEvent.getEventDate(), LocalDateTime.now().plusHours(2)));
        }
        newEvent.setInitiator(userService.getUserById(userId));
        newEvent.setCreatedOn(LocalDateTime.now());
        newEvent.setConfirmedRequests(0L);
        newEvent.setViews(0L);
        newEvent.setState(State.PENDING);
        newEvent.setCategory(categoryService.getCategoryById(dto.getCategory()));
        Event saved = repository.save(newEvent);
        return mapper.toEventFullDto(saved);
    }

    @Override
    public EventFullDto getById(Long userId, Long eventId) {
        Event founded = getEventById(userId, eventId);
        return mapper.toEventFullDto(founded);
    }

    private Event getEventById(Long userId, Long eventId) {
        return repository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new EventNotFoundException(String
                        .format("У пользователя с id = %d нет события с id = %d", userId, eventId)));
    }

    @Override
    public EventFullDto updateByUser(Long userId, Long eventId, UpdateEventUserRequestDto dto) {
        Event founded = getEventById(userId, eventId);
        if (founded.getState().equals(State.PUBLISHED)) {
            throw new ConflictException(String.format("Изменить можно только отмененные события " +
                            "или события в состоянии ожидания модерации текущее состояние события с id = %d " +
                            "state = %s.",
                    eventId, founded.getState().name()));
        }
        updateEventFields(founded, dto.getAnnotation(), dto.getCategory(), dto.getDescription(), dto.getEventDate(),
                dto.getLocation(), dto.getPaid(), dto.getParticipantLimit(), dto.getRequestModeration(),
                dto.getTitle());
        if (dto.getStateAction() != null) {
            if (dto.getStateAction().equals(UserStateAction.SEND_TO_REVIEW)) {
                founded.setState(State.PENDING);
            } else {
                founded.setState(State.CANCELED);
            }
        }
        Event saved = repository.save(founded);
        return mapper.toEventFullDto(saved);
    }

    @Override
    public List<EventFullDto> getAllByAdmin(List<Long> users, List<State> states, List<Long> categories,
                                            String rangeStart, String rangeEnd, int from, int size) {
        MainPage page = new MainPage(from, size, Sort.unsorted());

        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();

        if (users != null && !users.isEmpty()) {
            conditions.add(event.initiator.id.in(users));
        }
        if (states != null && !states.isEmpty()) {
            conditions.add(event.state.in(states));
        }
        if (categories != null && !categories.isEmpty()) {
            conditions.add(event.category.id.in(categories));
        }
        if (rangeStart != null) {
            conditions.add(event.eventDate.after(LocalDateTime
                    .parse(rangeStart, DateTimeFormatter.ofPattern(PATTERN))));
        }
        if (rangeEnd != null) {
            conditions.add(event.eventDate.before(LocalDateTime
                    .parse(rangeEnd, DateTimeFormatter.ofPattern(PATTERN))));
        }

        List<Event> events;
        if (conditions.isEmpty()) {
            return new ArrayList<>();
        } else {
            BooleanExpression expression = conditions.stream()
                    .reduce(BooleanExpression::and)
                    .get();

            events = StreamSupport.stream(repository.findAll(expression, page).spliterator(), false)
                    .collect(Collectors.toList());
        }
        return events.stream()
                .map(mapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto updateByAdmin(Long eventId, UpdateEventAdminRequestDto dto) {
        Event founded = getEventById(eventId);
        updateEventFields(founded, dto.getAnnotation(), dto.getCategory(), dto.getDescription(), dto.getEventDate(),
                dto.getLocation(), dto.getPaid(), dto.getParticipantLimit(), dto.getRequestModeration(),
                dto.getTitle());
        if (dto.getStateAction().equals(AdminStateAction.PUBLISH_EVENT)) {
            if (!founded.getState().equals(State.PENDING)) {
                throw new ConflictException("Событие можно публиковать, только если оно " +
                        "в состоянии ожидания публикации");
            }
            founded.setState(State.PUBLISHED);
        } else {
            if (founded.getState().equals(State.PUBLISHED)) {
                throw new ConflictException("Событие можно отклонить, только если оно еще не опубликовано");
            }
            founded.setState(State.CANCELED);
        }
        Event saved = repository.save(founded);
        return mapper.toEventFullDto(saved);
    }

    @Override
    public Event getEventById(Long eventId) {
        return repository.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(String.format("Событие с id = %d не найдено", eventId)));
    }

    private void updateEventFields(Event founded, String annotation, Long category, String description,
                                   String eventDate, Location location, Boolean paid, Long participantLimit,
                                   Boolean requestModeration, String title) {
        if (annotation != null) {
            founded.setAnnotation(annotation);
        }
        if (category != null) {
            founded.setCategory(categoryService.getCategoryById(category));
        }
        if (description != null) {
            founded.setDescription(description);
        }
        if (eventDate != null) {
            LocalDateTime dateTime = LocalDateTime.parse(eventDate, DateTimeFormatter.ofPattern(PATTERN));
            if (dateTime.isBefore(LocalDateTime.now().plusHours(1))) {
                throw new ConflictException(String.format("Дата начала изменяемого события (%s) должна быть " +
                        "не ранее чем за час от даты публикации (%s).", dateTime, LocalDateTime.now().plusHours(1)));
            }
            founded.setEventDate(dateTime);
        }
        if (location != null) {
            founded.setLocation(location);
        }
        if (paid != null) {
            founded.setPaid(paid);
        }
        if (participantLimit != null) {
            founded.setParticipantLimit(participantLimit);
        }
        if (requestModeration != null) {
            founded.setRequestModeration(requestModeration);
        }
        if (title != null) {
            founded.setTitle(title);
        }
    }

    @Override
    public List<EventShortDto> getAllByPublicQuery(String text, List<Long> categories, Boolean paid, String rangeStart,
                                                   String rangeEnd, Boolean onlyAvailable, String sort, int from,
                                                   int size, HttpServletRequest request) {
        client.createHit(statsMapper.toStatsCreateDto(request));
        Sort sortBy = Sort.unsorted();
        if (sort != null) {
            if (SortForPubEventsController.valueOf(sort).equals(SortForPubEventsController.EVENT_DATE)) {
                sortBy = Sort.by("eventDate");
            } else if (SortForPubEventsController.valueOf(sort).equals(SortForPubEventsController.VIEWS)) {
                sortBy = Sort.by("views");
            } else {
                throw new BadRequestException(String.format("Указан неверный тип сортировки событий (%s)", sort));
            }
        }
        MainPage page = new MainPage(from, size, sortBy);

        QEvent event = QEvent.event;

        List<BooleanExpression> conditions = new ArrayList<>();

        conditions.add(event.state.eq(State.PUBLISHED));
        if (text != null && !text.isEmpty()) {
            conditions.add(event.annotation.toLowerCase().like('%' + text.toLowerCase() + '%')
                    .or(event.description.toLowerCase().like('%' + text.toLowerCase() + '%')));
        }
        if (categories != null && !categories.isEmpty()) {
            conditions.add(event.category.id.in(categories));
        }
        if (paid != null) {
            conditions.add(event.paid.eq(paid));
        }
        if (rangeStart != null && !rangeStart.isEmpty()) {
            conditions.add(event.eventDate.after(LocalDateTime.parse(rangeStart,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        if (rangeEnd != null && !rangeEnd.isEmpty()) {
            conditions.add(event.eventDate.before(LocalDateTime.parse(rangeEnd,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
        if ((rangeStart == null || rangeStart.isEmpty()) && (rangeEnd == null || rangeEnd.isEmpty())) {
            conditions.add(event.eventDate.after(LocalDateTime.now()));
        }
        if (onlyAvailable == Boolean.TRUE) {
            conditions.add(event.participantLimit.gt(event.confirmedRequests));
        }

        BooleanExpression expression = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();

        return StreamSupport.stream(repository.findAll(expression, page).spliterator(), false)
                .map(mapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getByIdPublic(Long eventId, HttpServletRequest request) {
        client.createHit(statsMapper.toStatsCreateDto(request));
        Event event = getEventById(eventId);
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException(String.format("Событие с id=%d еще не опубликовано", eventId));
        }

        event.setViews(event.getViews() + 1);
        repository.save(event);

        return mapper.toEventFullDto(event);
    }
}
