package codestates.main22.user.entity;

import codestates.main22.auditable.Auditable;
import codestates.main22.personalDm.entity.PersonalDmEntity;
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
@Entity(name = "USERS")
public class UserEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 10, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    //@Column
    @Lob
    private String info;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role = new ArrayList<>();

    @Column
    private String imgUrl;

    @Column
    private String token;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();


    // 연관관계 매핑 - 한 user 에 여러개의 personalDm
    @OneToMany(mappedBy = "user")
    private List<PersonalDmEntity> personalDms = new ArrayList<>();

    // 연관관계 매핑 - 한 user 에 여러개의 userStudy
    @OneToMany(mappedBy = "user")
    private List<UserStudyEntity> userStudies = new ArrayList<>();
}
