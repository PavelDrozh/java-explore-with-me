package ru.practicum.explorewithme.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "locator", schema = "public")
public class Locator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    Float lat;
    @Column(nullable = false)
    Float lon;
    @Column(nullable = false)
    Float distance;
    @Column(nullable = false)
    String name;
}
