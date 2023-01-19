package codestates.main22.calendar.dto;

import codestates.main22.participant.dto.ParticipantResponseDto;
import codestates.main22.participant.entity.Participant;
import codestates.main22.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class CalendarResponseDto {
    public long calendarId;
    private String title;
    private LocalDateTime date;
    private List<ParticipantResponseDto> participants;
}
