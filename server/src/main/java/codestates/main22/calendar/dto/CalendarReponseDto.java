package codestates.main22.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CalendarReponseDto {
    @AllArgsConstructor
    @Getter
    public static class Post {
        public long calendarId;
        private LocalDateTime dateTime;
        private String content;
        private Map<String, String> participant = new HashMap<>();
    }
}
