package codestates.main22.participant.entity;

import codestates.main22.calendar.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long participantId;

//    @Column(nullable = false)
    private long userId;

//    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    private Attendance joinState = Attendance.NONE;

    @Getter
    public static enum Attendance {
        YES("참가"),
        NO("불참"),
        NONE("생각 중");

        @Getter
        private String state;

        Attendance(String state) {
            this.state = state;
        }
    }

    // 연관관계 매핑 - 한 calendar 에 여러개의 participant
    @ManyToOne
    @JoinColumn(name = "CALENDAR_ID")
    private Calendar calendar;

    // 생성자
    public Participant(long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
