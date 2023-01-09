package codestates.main22.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class CalendarRequestDto {
    @AllArgsConstructor
    @Getter
    public static class Post{
        private LocalDate date;
        private LocalTime time;
        private String content;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        public long calendarId;
        private LocalDate date;
        private LocalTime time;
        private String content;
        private Map<String, String> participant = new HashMap<>();
    }
}