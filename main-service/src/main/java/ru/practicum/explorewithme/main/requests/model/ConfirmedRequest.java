package ru.practicum.explorewithme.main.requests.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmedRequest {

    private Long eventId;
    private Long countConfirmedRequest;
}
