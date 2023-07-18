package ru.practicum.explorewithme.main.utility;

import static org.apache.commons.lang3.math.NumberUtils.toLong;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.EnumUtils;
import ru.practicum.explorewithme.main.event.model.enums.EventState;

public class GeneralUtility {

    public static List<Long> convertToLongFromString(String inputString) {
        if (inputString == null || inputString.isBlank()) {
            return null;
        }
        List<Long> longList = new ArrayList<>();

        for (String s : inputString.split(",")) {
            if (toLong(s.trim()) != 0) {
                longList.add(toLong(s.trim()));
            }
        }
        return longList;
    }

    public static List<EventState> convertToListEventStateFromString(String inputString) {
        if (inputString == null || inputString.isBlank()) {
            return null;
        }
        List<EventState> eventStateList = new ArrayList<>();
        for (String es : inputString.split(",")) {
            if (EnumUtils.getEnum(EventState.class, es.trim()) != null) {
                eventStateList.add(EnumUtils.getEnum(EventState.class, es.trim()));
            }
        }
        return eventStateList;
    }
}
