package codestates.main22.answer.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.chat.entity.ChatEntity;
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
@Entity(name = "ANSWER")
public class AnswerEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column(nullable = false)
    private long answerUserId;

    //@Column
    @Lob
    private String content;

    @Column(nullable = false)
    private Boolean isClosedChat; // 단일로만 사용했을때 postman 오류가 나서 임시로 넣음


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();


    // 연관관계 매핑 - 한 chat 에 여러개의 answer
    @ManyToOne
    @JoinColumn(name = "CHAT_ID")
    private ChatEntity chat;
}
