package codestates.main22.message.entity;

import codestates.main22.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Message extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long messageId;

    @Column(length = 300, nullable = false)
    public String content;

    @Column(nullable = false)
    public LocalDateTime dateTime;

    @Column(nullable = false, updatable = false)
    public long userId;
}
