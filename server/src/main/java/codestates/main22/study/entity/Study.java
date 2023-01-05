package codestates.main22.study.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Study {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studyId;
    @Column(nullable = false)
    private String title;
    private String dayOfWeek;
    private int want;
    private Boolean[] startDate;
    private boolean procedure;
    private boolean openClose;
    private String contact;
    @Column(nullable = false)
    private String content;
    private String notice;
    private String image;
}
