package codestates.main22.tree.entity;

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
//@Entity(name = "TREETABLE")
public class Tree extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long treeId;

    @Column
    private int treePoint = 0;

    @Column
    private String treeImage;

    @Column
    private int makeMonth;

    // 연관관계 매핑 - 한 study 에 여러 개의 tree
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;

    public void setStudy(Study study) {
        this.study = study;
        if(!this.study.getTrees().contains(this))
            this.study.getTrees().add(this);
    }
}
