package ru.practicum.explorewithme.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

import static ru.practicum.explorewithme.model.DbConstants.*;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = STATS_TABLE, schema = "public")
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = APP_COLUMN)
    String app;

    @Column(name = URI_COLUMN)
    String uri;

    @Column(name = IP_COLUMN)
    String ip;

    @Column(name = CREATE_COLUMN)
    LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        EndpointHit that = (EndpointHit) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
