package ru.practicum.explorewithme.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateHitDto {

    private long id;

    @NotBlank
    @Length(max = 255)
    private String app;

    @NotBlank
    @Length(max = 255)
    private String uri;

    @NotBlank
    @Length(max = 50)
    private String ip;

    @NotBlank
    private String timestamp;
}
