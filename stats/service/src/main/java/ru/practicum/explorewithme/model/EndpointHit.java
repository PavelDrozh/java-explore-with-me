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
        boolean appEqual = app != null && Objects.equals(app, that.app);
        boolean uriEqual = uri != null && Objects.equals(uri, that.uri);
        boolean ipEqual = ip != null && Objects.equals(ip, that.ip);
        boolean timeEqual = timestamp != null && Objects.equals(timestamp, that.timestamp);
        return id != null && Objects.equals(id, that.id) && appEqual && uriEqual && ipEqual && timeEqual;
    }

    @Override
    public int hashCode() {
        int idHash = id.hashCode();
        int appHash = app.hashCode();
        int uriHash = uri.hashCode();
        int ipHash = ip.hashCode();
        int timeHash = timestamp.hashCode();
        return idHash * 31 + appHash * 31 + uriHash * 31 + ipHash * 31 + timeHash * 31;
    }
}
