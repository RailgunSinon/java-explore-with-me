package ru.practicum.explorewithme.main.event.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.enums.EventState;
import ru.practicum.explorewithme.main.user.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "annotation", nullable = false)
    String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @Transient
    Long confirmedRequests;

    @Column(name = "created_on", nullable = false)
    LocalDateTime createdOn;

    @Column(name = "description")
    String description;

    @Column(name = "event_date")
    LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    Location location;

    @Column(name = "paid", nullable = false)
    Boolean paid;

    @Column(name = "participant_limit")
    Long participantLimit;

    @Column(name = "published_on")
    LocalDateTime publishedOn;

    @Column(name = "request_moderation")
    Boolean requestModeration;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    EventState state;

    @Column(name = "title")
    String title;

    @Transient
    Long views = 0L;
}