package ru.practicum.explorewithme.client;

import java.net.URLEncoder;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.client.dto.StateHitDto;
import ru.practicum.explorewithme.client.dto.StateViewDto;

@RequiredArgsConstructor
public class StatClient {

    private final RestTemplate restTemplate;

    public ResponseEntity<StateHitDto> hit(StateHitDto stateHitDto) {
        return restTemplate.exchange("/hit", HttpMethod.POST,
            new HttpEntity<>(stateHitDto), StateHitDto.class);
    }

    public ResponseEntity<List<StateViewDto>> stats(String start, String end,
        List<String> uris, Boolean unique) {
        String params = "?start=" + URLEncoder.encode(start) + "&end=" + URLEncoder.encode(end);
        for (String uri : uris) {
            params += "&uris=" + uri;
        }
        if (unique != null) {
            params += "&unique=" + unique;
        }
        return restTemplate.exchange("/stats" + params, HttpMethod.GET, HttpEntity.EMPTY,
            new ParameterizedTypeReference<List<StateViewDto>>() {
            });
    }
}
