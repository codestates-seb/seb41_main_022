package codestates.main22.message.repository;

import codestates.main22.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByStudyId(long studyId);
}