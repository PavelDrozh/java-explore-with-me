package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.explorewithme.model.Event;
import ru.practicum.explorewithme.model.State;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event>,
        QuerydslPredicateExecutor<Event> {

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long id);

    List<Event> findAllByIdIn(List<Long> id);

    List<Event> findAllByCategoryId(Long categoryId);

    @Query("select e from Event e " +
            "where function('distance', e.location.lat, e.location.lon, ?1, ?2) < ?3")
    List<Event> findAllByLocator(Float locLat, Float locLon, Float distance);

    @Query("select e from Event e " +
            "where function('distance', e.location.lat, e.location.lon, ?1, ?2) < ?3 " +
            "and e.initiator.id = ?4")
    List<Event> findAllByLocatorAndUser(Float locLat, Float locLon, Float distance, Long userId);

    @Query("select e from Event e " +
            "where function('distance', e.location.lat, e.location.lon, ?1, ?2) < ?3 " +
            "and e.state = ?4")
    List<Event> findAllPubByLocator(Float locLat, Float locLon, Float distance, State state);

}
