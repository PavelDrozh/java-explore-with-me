package ru.practicum.explorewithme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.model.Locator;

import java.util.List;
import java.util.Optional;

public interface LocatorRepository extends JpaRepository<Locator, Long> {

    @Query("select l from Locator l " +
            "where l.distance > function('distance', l.lat, l.lon, ?1, ?2)")
    Optional<Locator> findByLatAndLonInDistance(Float lat, Float lon);

    @Query("select l from Locator l where upper(l.name) like upper(concat('%', ?1, '%'))")
    List<Locator> findAllByName(String name, Pageable pageable);

}
