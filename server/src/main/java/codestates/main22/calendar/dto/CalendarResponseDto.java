package codestates.main22.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

public class CalendarResponseDto {
    @AllArgsConstructor
    @Getter
    public static class Post {
        public long calendarId;
        private LocalDate date;
        private LocalTime time;
        private String content;
        private Map<String, String> participant = new HashMap<>();
    }
}
