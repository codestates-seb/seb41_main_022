package codestates.main22.calendar.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.study.entity.Study;
import codestates.main22.user.entity.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Calendar extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long calendarId;

    @Column(nullable = false)
    private LocalDate dateTime;

    @Column(length = 100, nullable = false)
    private String title;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Long, String> participant = new HashMap<>(); // Map<Long, String> : Long은 userId, String은 attendance의 값

    @Getter
    public static enum attendance {
        YES("참가"),
        NO("불참"),
        NONE("생각 중");

        @Getter
        private String state;

        attendance(String state) {
            this.state = state;
        }
    }


    // 연관관계 매핑 - 한 study 에 여러개의 calendar
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;
}