package ru.practicum.explorewithme.statserver.controller.mapper;

import ru.practicum.explorewithme.dto.StatDto;
import ru.practicum.explorewithme.statserver.model.Stat;

public class StatMapper {
    public static StatDto toStatDto(Stat stat) {
        return StatDto.builder()
            .app(stat.getApp())
            .uri(stat.getUri())
            .hits(stat.getHits())
            .build();
    }
}
