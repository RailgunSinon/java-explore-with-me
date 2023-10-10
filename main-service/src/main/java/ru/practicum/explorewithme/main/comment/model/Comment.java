package ru.practicum.explorewithme.main.comment.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.main.event.model.Event;
import ru.practicum.explorewithme.main.user.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "text")
    String text;

    @ManyToOne
    @JoinColumn(name = "commentator_id", nullable = false)
    User commentator;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    @Column(name = "created")
    LocalDateTime created;

    @Column(name = "updated")
    LocalDateTime updated;
}