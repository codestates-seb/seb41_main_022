package codestates.main22.study.entity;

import codestates.main22.calendar.entity.Calendar;
import codestates.main22.hashtag.entity.HashtagEntity;
import codestates.main22.message.entity.Message;
import codestates.main22.tag.entity.TagStudy;
import codestates.main22.tree.entity.Tree;
import codestates.main22.user.entity.UserStudyEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studyId;

    private String teamName;

    @Column(nullable = false)
    private String title;

    private boolean[] dayOfWeek;

    private int want;

    private String startDate;

    private boolean procedure;

    private boolean openClose;

    private String contact;

    @Column(nullable = false)
    private String content;

    private String notice;

    private String image;


    // 연관관계 매핑 - 한 study 에 여러개의 calendar
    @OneToMany(mappedBy = "study")
    private List<Calendar> calendars = new ArrayList<>();


    // 연관관계 매핑 - 한 study 에 여러개의 message
    @OneToMany(mappedBy = "study")
    private List<Message> messages = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 hashTag
    @OneToMany(mappedBy = "study")
    private List<HashtagEntity> hashtags = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 userStudy
    @OneToMany(mappedBy = "study")
    private List<UserStudyEntity> userStudies = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 tagStudy
    @OneToMany(mappedBy = "study")
    private List<TagStudy> tagStudies = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 한 개의 tree
    @OneToOne(mappedBy = "study")
    private Tree tree;
}
