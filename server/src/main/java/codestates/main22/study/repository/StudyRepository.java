package codestates.main22.study.repository;

import codestates.main22.study.entity.Study;
import codestates.main22.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    List<Study> findByUserStudiesUser(UserEntity user);

}
