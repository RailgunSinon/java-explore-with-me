package ru.practicum.explorewithme.client.controller.mapper;

import static ru.practicum.explorewithme.client.GlobalStaticProperties.DATE_FORMAT;

import java.time.LocalDateTime;
import ru.practicum.explorewithme.client.dto.StateHitDto;
import ru.practicum.explorewithme.client.model.Hit;

public class StateMapper {
    public Hit toEntity(StateHitDto stateHitDto){
        return Hit.builder()
            .id(stateHitDto.getId())
            .app(stateHitDto.getApp())
            .uri(stateHitDto.getUri())
            .ip(stateHitDto.getIp())
            .created(LocalDateTime.parse(stateHitDto.getTimestamp(),DATE_FORMAT))
            .build();
    }
    
}
