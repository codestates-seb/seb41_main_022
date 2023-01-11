package codestates.main22.message.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.message.entity.Message;
import codestates.main22.message.repository.MessageRepository;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

//@Transactional
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private StudyService studyService;
    private final UserRepository userRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    public MessageService(MessageRepository messageRepository, StudyService studyService,
                          UserRepository userRepository, CustomAuthorityUtils customAuthorityUtils) {
        this.messageRepository = messageRepository;
        this.studyService = studyService;
        this.userRepository = userRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    // 메세지 생성
    @Transactional
    public Message createMessage(long studyId, Message message, HttpServletRequest request) {
        // 유저가 스터디에 USER 권한이 있는지 찾고 없으면 exception 발생
        UserEntity user = userRepository.findByToken(request);
        String admin = "STUDY" + studyId + "_ADMIN"; // 관리자인지 확인
        String user1 = "STUDY" + studyId + "_USER"; // 가입된 유저인지 확인
        if(!(user.getRole().contains(admin) || user.getRole().contains(user1))) {new BusinessLogicException(ExceptionCode.UNREGISTERED_USER);} //스터디원만 허용
        Study findStudy = studyService.findStudy(studyId);
        message.setStudy(findStudy);
        messageRepository.save(message);

        return message;
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
    public Page<Message> findMessages(int page, int size) {
        return messageRepository.findAll(PageRequest.of(page, size,
                Sort.by("messageId").descending()));
    }

    // 메세지 증명
    public Message verifiedMessage(long messageId) {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        Message message =
                optionalMessage.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MESSAGE_NOT_FOUND));

        return message;
    }

    // 스터디 아이디 찾기 (스터디별 채팅 보기 기능에 사용)
    public List<Message> findByStudy(long studyId) {
        Study findStudy = studyService.VerifiedStudy(studyId);
        return messageRepository.findByStudy(findStudy);
    }
}
