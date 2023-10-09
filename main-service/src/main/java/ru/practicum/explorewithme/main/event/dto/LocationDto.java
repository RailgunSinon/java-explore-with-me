package ru.practicum.explorewithme.main.event.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {
    @NotNull(message = "Широта должна быть задана!")
    Float lat;
    @NotNull(message = "Долгота должна быть задана!")
    Float lon;
}