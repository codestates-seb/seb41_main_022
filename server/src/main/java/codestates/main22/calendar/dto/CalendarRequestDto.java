package codestates.main22.calendar.dto;

import codestates.main22.participant.dto.ParticipantRequestDto;
import codestates.main22.participant.dto.ParticipantResponseDto;
import codestates.main22.participant.entity.Participant;
import codestates.main22.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarRequestDto {
    @AllArgsConstructor
    @Getter
    public static class Post{
        private String title;
        private LocalDateTime date;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch {
        public long calendarId;
        private String title;
        private LocalDateTime date;
        private List<ParticipantRequestDto> participants;
    }
}