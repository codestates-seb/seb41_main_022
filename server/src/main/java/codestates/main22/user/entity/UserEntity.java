package codestates.main22.user.entity;

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
@Entity(name = "USERS")
public class UserEntity extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(length = 10, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    //@Column
    @Lob
    private String info;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> role = new ArrayList<>();

    @Column
    private String imgUrl;

    @Column(length = 300)
    private String token;

    @Column(length = 300)
    private String refresh;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime modifiedAt = LocalDateTime.now();


    // 연관관계 매핑 - 한 user 에 여러개의 userStudy
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<UserStudyEntity> userStudies = new ArrayList<>();

    public UserEntity(String email, String name, String imgUrl) {
        this.email = email;
        this.username = name;
        this.imgUrl = imgUrl;
    }

    public void removeStudy(Study study) {
        userStudies.removeIf(userStudy -> userStudy.getStudy().equals(study));
    }

}