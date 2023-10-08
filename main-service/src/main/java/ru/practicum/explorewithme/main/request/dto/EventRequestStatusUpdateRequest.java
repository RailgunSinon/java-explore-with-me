package ru.practicum.explorewithme.main.request.dto;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.explorewithme.main.enums.RequestStatus;

@Data
@Builder
public class EventRequestStatusUpdateRequest {

    @NotEmpty
    List<Long> requestIds;

    @NotNull
    RequestStatus status;
}