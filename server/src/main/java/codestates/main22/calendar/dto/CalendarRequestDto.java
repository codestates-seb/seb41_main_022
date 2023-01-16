package codestates.main22.calendar.dto;

import codestates.main22.user.entity.UserEntity;
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
        private LocalDateTime dateTime;
        private String title;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        public long calendarId;
        private LocalDateTime dateTime;
        private String title;
        private Map<Long, String> participant = new HashMap<>();
    }
}