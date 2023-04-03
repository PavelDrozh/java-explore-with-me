package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.EventRequest;

import java.util.List;
import java.util.Optional;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {

    List<EventRequest> findAllByRequesterId(Long requesterId);

    Optional<EventRequest> findByRequesterIdAndEventId(Long requester, Long eventId);

    List<EventRequest> findAllByEventId(Long eventId);

    List<EventRequest> findAllByEventIdAndIdIn(Long eventId, List<Long> id);
}
