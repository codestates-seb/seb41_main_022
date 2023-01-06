package codestates.main22.hashtag.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "HASHTAG")
public class HashtagEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long hashtagId;

    @Column(unique = true, length = 10, nullable = false)
    private String name;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();


    // 연관관계 매핑 - 한 study 에 여러개의 hashTag
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;

}