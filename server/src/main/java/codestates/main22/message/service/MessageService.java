package codestates.main22.message.service;

import codestates.main22.message.entity.Message;
import codestates.main22.message.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 캘린더 생성
    public Message createMessage(Message message) {
        return new Message();
    }
}
