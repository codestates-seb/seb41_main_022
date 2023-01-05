package codestates.main22.tag.entity;

import codestates.main22.auditable.Auditable;
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
public class Tag extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagId;

    @Column(length = 50, nullable = false, unique = true)
    private String name;


    // 연관관계 매핑 - 한 tag 에 여러개의 tagStudy
    @OneToMany(mappedBy = "tag")
    private List<TagStudy> tagStudies = new ArrayList<>();
}
