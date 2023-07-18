package ru.practicum.explorewithme.main.utility;

import java.time.format.DateTimeFormatter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GlobalStaticProperties {

    public static final DateTimeFormatter DATE_FORMAT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
