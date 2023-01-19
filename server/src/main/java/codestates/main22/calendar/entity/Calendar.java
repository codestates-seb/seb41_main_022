package codestates.main22.calendar.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.participant.entity.Participant;
import codestates.main22.study.entity.Study;
import lombok.*;

import javax.persistence.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Calendar extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long calendarId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime date;


    // 연관관계 매핑 - 한 study 에 여러개의 calendar
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;

    // 연관관계 매핑 - 한 calendar 에 여러개의 participant
    @OneToMany(mappedBy = "calendar", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Participant> participants = new ArrayList<>();

}