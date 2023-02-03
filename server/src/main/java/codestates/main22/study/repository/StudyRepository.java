package codestates.main22.study.repository;

import codestates.main22.study.entity.Study;
import codestates.main22.tag.entity.Tag;
import codestates.main22.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long>, JpaSpecificationExecutor<Study> {
    List<Study> findByUserStudiesUser(UserEntity user);

    List<Study> findByTagStudiesTag(Tag tag);
}

