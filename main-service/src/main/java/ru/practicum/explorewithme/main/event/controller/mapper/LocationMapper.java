package ru.practicum.explorewithme.main.event.controller.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.main.event.dto.LocationDto;
import ru.practicum.explorewithme.main.event.model.Location;

@Component
public class LocationMapper {

    public LocationDto toLocationDto(Location location) {
        return new LocationDto(location.getLat(), location.getLon());
    }

    public List<LocationDto> toLocationDtoList(List<Location> locations) {
        List<LocationDto> locationDtos = new ArrayList<>();
        for (Location location : locations) {
            locationDtos.add(toLocationDto(location));
        }
        return locationDtos;
    }

    public Location toLocation(LocationDto locationDto) {
        return new Location(locationDto.getLat(), locationDto.getLon());
    }
}
