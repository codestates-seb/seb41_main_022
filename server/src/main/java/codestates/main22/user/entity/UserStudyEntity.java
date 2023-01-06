package codestates.main22.user.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.study.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
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
}
