package codestates.main22.tag.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.study.entity.Study;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class TagStudy extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagStudyId;


    // 연관관계 매핑 - 한 tag 에 여러개의 tagStudy
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    // 연관관계 매핑 - 한 study 에 여러개의 tagStudy
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;
}
