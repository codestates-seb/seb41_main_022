package codestates.main22.user.repository;

import codestates.main22.study.entity.Study;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.entity.UserStudyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStudyRepository extends JpaRepository<UserStudyEntity, Long> {
    List<UserStudyEntity> findByStudy(Study study);
    List<UserStudyEntity> findByUser(UserEntity user);
    UserStudyEntity findByUserAndStudy(UserEntity user, Study study);
}
