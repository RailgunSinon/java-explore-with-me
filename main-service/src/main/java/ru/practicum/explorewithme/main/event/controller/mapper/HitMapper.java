package ru.practicum.explorewithme.main.event.controller.mapper;

import static ru.practicum.explorewithme.main.utility.GlobalStaticProperties.DATE_FORMAT;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.StateHitDto;
import ru.practicum.explorewithme.main.event.model.Hit;


@Component
public class HitMapper {

    public Hit toEntity(StateHitDto stateHitDto) {
        return Hit.builder()
            .id(stateHitDto.getId())
            .app(stateHitDto.getApp())
            .uri(stateHitDto.getUri())
            .ip(stateHitDto.getIp())
            .created(LocalDateTime.parse(stateHitDto.getTimestamp(), DATE_FORMAT))
            .build();
    }

    public StateHitDto toDto(Hit hit) {
        return StateHitDto.builder()
            .id(hit.getId())
            .app(hit.getApp())
            .uri(hit.getUri())
            .ip(hit.getIp())
            .timestamp(hit.getCreated().format(DATE_FORMAT))
            .build();
    }
}
