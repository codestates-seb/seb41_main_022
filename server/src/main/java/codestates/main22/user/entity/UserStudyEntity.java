package codestates.main22.user.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.study.entity.Study;
import codestates.main22.tag.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "USER_STUDY")
public class UserStudyEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userStudyId;


    // 연관관계 매핑 - 한 user 에 여러개의 userStudy
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    // 연관관계 매핑 - 한 study 에 여러개의 userStudy
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;

    public void setUser(UserEntity user) {
        this.user = user;
        if(!this.user.getUserStudies().contains(this))
            this.user.getUserStudies().add(this);
    }

    public void setStudy(Study study) {
        this.study = study;
        if(!this.study.getUserStudies().contains(this))
            this.study.getUserStudies().add(this);
    }
}
