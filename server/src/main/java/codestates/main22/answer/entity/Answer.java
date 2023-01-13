package codestates.main22.answer.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.chat.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Answer extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column
    private long answerUserId;

    @Column(nullable = false)
    private String content;

    // 연관관계 매핑 - 한 chat 에 여러개의 answer
    @ManyToOne
    @JoinColumn(name = "CHAT_ID")
    private Chat chat;
}
