package codestates.main22.tree.repository;

import codestates.main22.study.entity.Study;
import codestates.main22.tree.entity.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByStudy(Study study);
}
