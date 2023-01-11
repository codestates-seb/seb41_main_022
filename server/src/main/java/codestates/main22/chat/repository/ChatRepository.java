package codestates.main22.chat.repository;

import codestates.main22.chat.entity.ChatEntity;
import codestates.main22.study.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatEntity, Long> {
    Page<ChatEntity> findByStudy(Study study, PageRequest pageRequest);
}
