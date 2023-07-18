package ru.practicum.explorewithme.main.requests.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.requests.model.enums.RequestUpdateState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateDto {

    private List<Long> requestIds;
    private RequestUpdateState status;
}
