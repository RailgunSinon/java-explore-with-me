package ru.practicum.explorewithme.statserver.controller.mapper;

import static ru.practicum.explorewithme.statserver.GlobalStaticProperties.DATE_FORMAT;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.statserver.model.Hit;

@Component
public class HitMapper {

    public static Hit toHit(HitDto hitDto) {
        return Hit.builder()
            .app(hitDto.getApp())
            .uri(hitDto.getUri())
            .ip(hitDto.getIp())
            .created(LocalDateTime.parse(hitDto.getTimestamp(),DATE_FORMAT))
            .build();
    }

    public static HitDto toDto(Hit hit) {
        return HitDto.builder()
            .app(hit.getApp())
            .uri(hit.getUri())
            .ip(hit.getIp())
            .timestamp(hit.getCreated().format(DATE_FORMAT))
            .build();
    }
}
