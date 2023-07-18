package ru.practicum.explorewithme.main.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.category.model.Category;
import ru.practicum.explorewithme.main.event.model.enums.EventState;
import ru.practicum.explorewithme.main.user.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(max = 120)
    @Size(min = 3)
    private String title;

    @NotEmpty
    @Size(max = 7000)
    @Size(min = 20)
    private String description;

    @NotEmpty
    @Size(max = 2000)
    @Size(min = 20)
    String annotation;

    @Enumerated(EnumType.STRING)
    EventState state;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;

    Integer confirmedRequests;

    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    boolean paid;

    Integer participantLimit;

    boolean requestModeration;
}
