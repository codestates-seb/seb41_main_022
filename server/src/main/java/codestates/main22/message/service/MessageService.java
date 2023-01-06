package codestates.main22.message.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.message.entity.Message;
import codestates.main22.message.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 메세지 생성
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    // 메세지 수정
    public Message updateMessage(Message message) {
        Message findMessage = verifiedMessage(message.getMessageId());

        Optional.ofNullable(message.getContent())
                .ifPresent(content -> findMessage.setContent(content));
        Optional.ofNullable(message.getDateTime())
                .ifPresent(localDateTime -> findMessage.setDateTime(localDateTime));
        Optional.ofNullable(message.getUserId())
                .ifPresent(userId -> findMessage.setUserId(userId));

        return messageRepository.save(findMessage);
    }

    // 메세지 삭제
    public void deleteMessage(long messageId) {
        Message message = verifiedMessage(messageId);
        messageRepository.delete(message);
    }

    // 메세지 조회
    public Message findMessage(long messageId) {
        return verifiedMessage(messageId);
    }

    // 메세지 전체 조회
    public List<Message> findMessages() {
        return messageRepository.findAll();
    }

    // 메세지 증명
    public Message verifiedMessage(long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        Message message =
                optionalMessage.orElseThrow(() ->
                        new BusinessLogicException((ExceptionCode.MESSAGE_NOT_FOUND)));

        return message;
    }

}
