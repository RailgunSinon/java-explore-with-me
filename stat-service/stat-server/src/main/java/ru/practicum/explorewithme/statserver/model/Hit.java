package ru.practicum.explorewithme.statserver.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "hits")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(nullable = false, length = 255)
    String app;
    @Column(nullable = false, length = 255)
    String uri;
    @Column(nullable = false, length = 50)
    String ip;
    @Column(nullable = false)
    LocalDateTime created;
}
