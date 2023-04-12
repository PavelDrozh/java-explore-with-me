package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.EventsArea;

import java.util.List;
import java.util.Optional;

public interface EventsAreaRepository extends JpaRepository<EventsArea, Long> {

    @Query("select l from EventsArea l where upper(l.name) like upper(concat('%', ?1, '%'))")
    List<EventsArea> findAllByName(String name, Pageable pageable);


    @Query("select l from EventsArea l " +
            "where l.distance > function('distance', l.lat, l.lon, ?1, ?2)")
    Optional<EventsArea> findByLatAndLonInDistance(Float lat, Float lon);

}
