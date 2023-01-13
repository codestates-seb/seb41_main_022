package codestates.main22.chat.repository;

import codestates.main22.chat.entity.Chat;
import codestates.main22.study.entity.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Page<Chat> findByStudy(Study study, PageRequest pageRequest);
}
