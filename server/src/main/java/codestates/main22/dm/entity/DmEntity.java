package codestates.main22.dm.entity;

import codestates.main22.auditable.Auditable;
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
@Entity(name = "DM")
public class DmEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dmId;

    @Column(nullable = false)
    private long currentUserId;

    //@Column
    @Lob  // "Large OBject를 DB에 적절하게 저장하기 위한 에노테이션"
    private String content;

    @Column(nullable = false)
    private LocalDateTime dateTime;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();
}
