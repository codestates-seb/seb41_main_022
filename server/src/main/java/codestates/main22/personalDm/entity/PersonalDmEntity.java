package codestates.main22.personalDm.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.dm.entity.DmEntity;
import codestates.main22.user.entity.UserEntity;
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
@Entity(name = "PERSONAL_DM")
public class PersonalDmEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personalDmId;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();


    // 연관관계 매핑 - 한 personalDm 에 여러개의 dm
    @OneToMany(mappedBy = "personalDm")
    private List<DmEntity> dms = new ArrayList<>();

    // 연관관계 매핑 - 한 user 에 여러개의 personalDm
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;
}
