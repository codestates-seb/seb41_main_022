package codestates.main22.calendar.dto;

import codestates.main22.user.entity.UserEntity;
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
        private LocalDateTime dateTime;
        private String title;
        private Map<Long, String> participant = new HashMap<>();
    }
}
