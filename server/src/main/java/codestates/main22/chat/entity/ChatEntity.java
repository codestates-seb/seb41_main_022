package codestates.main22.chat.entity;

import codestates.main22.answer.entity.AnswerEntity;
import codestates.main22.auditable.Auditable;
import codestates.main22.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CHAT")
public class ChatEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long chatId;

    @Column(nullable = false)
    private long chatUserId;

    //@Column
    @Lob
    private String content;

    @Column(nullable = false)
    private Boolean isClosedChat;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();


    // 연관관계 매핑 - 한 chat 에 여러개의 answer
    @OneToMany(mappedBy = "chat")
    private List<AnswerEntity> answers = new ArrayList<>();

    // 연관관계 매핑 - 한 study 에 여러개의 chat
    @ManyToOne
    @JoinColumn(name = "STUDY_ID")
    private Study study;
}
