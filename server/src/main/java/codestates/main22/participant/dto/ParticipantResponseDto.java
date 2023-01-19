package codestates.main22.participant.dto;

import codestates.main22.participant.entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ParticipantResponseDto {
    private long userId;
    private String username;
    private Participant.Attendance joinState;
}
