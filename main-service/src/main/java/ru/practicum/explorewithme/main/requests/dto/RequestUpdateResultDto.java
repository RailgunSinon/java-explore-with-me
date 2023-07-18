package ru.practicum.explorewithme.main.requests.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateResultDto {

    private List<RequestDto> confirmedRequests;
    private List<RequestDto> rejectedRequests;
}
