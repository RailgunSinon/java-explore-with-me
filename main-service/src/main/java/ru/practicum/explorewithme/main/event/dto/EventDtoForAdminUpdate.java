package ru.practicum.explorewithme.main.event.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.explorewithme.main.enums.AdminStateEventAction;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventDtoForAdminUpdate {

    @Size(min = 20, max = 2000)
    String annotation;
    Long category;
    @Size(min = 20, max = 7000)
    String description;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime eventDate;
    LocationDto location;
    Boolean paid;
    @PositiveOrZero
    Long participantLimit;
    Boolean requestModeration;
    AdminStateEventAction stateAction;
    @Size(min = 3, max = 120)
    String title;
}