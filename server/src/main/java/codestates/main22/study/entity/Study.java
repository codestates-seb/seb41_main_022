package codestates.main22.study.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.calendar.entity.Calendar;
import codestates.main22.chat.entity.Chat;
import codestates.main22.message.entity.Message;
import codestates.main22.tag.entity.TagStudy;
import codestates.main22.tree.entity.Tree;
import codestates.main22.user.entity.UserStudyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Study extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studyId;
    @Column(nullable = false)
    private String teamName;
    @Column(nullable = false)
    private String summary;
    @Column
    @ElementCollection
    private List<String> dayOfWeek = new ArrayList<>();
    @Column
    private int want;
    @Column
    private LocalDate startDate;
    @Column
    private boolean onOff;
    @Column
    private boolean openClose;
    @Column(nullable = false)
    private String content;
    @Column
    private String notice;
    @Column
    private String image;
    @Column(nullable = false)
    private long leaderId;
    @Column
    @ElementCollection
    private List<Long> requester;

    @Getter // enum 타입 dayOfWeek (주간 활동 표시)
    public enum dayOfWeek {
        SUN("일"),
        MON("월"),
        TUE("화"),
        WED("수"),
        THU("목"),
        FRI("금"),
        SAT("토");

        @Getter
        private String week;

        dayOfWeek(String week) {this.week = week;}
    }


    // 연관관계 매핑 - 한 study 에 여러개의 calendar
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Calendar> calendars = new ArrayList<>();


    // 연관관계 매핑 - 한 study 에 여러개의 message
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Message> messages = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 userStudy
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserStudyEntity> userStudies = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 tagStudy
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<TagStudy> tagStudies = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 tree
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Tree> trees = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 chat
    @OneToMany(mappedBy = "study", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Chat> chats = new ArrayList<>();

    public void deleteTagStudy(TagStudy tagStudy) {
        this.tagStudies.remove(tagStudy);
    }
}
