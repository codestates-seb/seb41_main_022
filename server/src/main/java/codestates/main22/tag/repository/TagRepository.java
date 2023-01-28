package codestates.main22.tag.repository;

import codestates.main22.study.entity.Study;
import codestates.main22.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTop1ByName(String name);
    List<Tag> findByTagStudiesStudy(Study study);
}