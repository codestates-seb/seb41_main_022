package codestates.main22.tree.entity;

import codestates.main22.study.entity.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long treeId;
    private int treePoint;
    private String treeImage;

    // 연관관계 매핑 - 한 study 에 한 개의 tree
    @OneToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;
}
