package codestates.main22.tag.entity;

import codestates.main22.auditable.Auditable;
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
}
