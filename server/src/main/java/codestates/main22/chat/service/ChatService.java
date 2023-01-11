package codestates.main22.chat.service;

import codestates.main22.chat.entity.ChatEntity;
import codestates.main22.chat.repository.ChatRepository;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.message.entity.Message;
import codestates.main22.study.entity.Study;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatService {
    private ChatRepository chatRepository;
    private StudyService studyService;

    //CRUD 순서에 맞춰서

    //CREATE
    public ChatEntity createChat(long studyId, ChatEntity chat) {
        Study findStudy = studyService.findStudy(studyId);
        chat.setStudy(findStudy);
        return chatRepository.save(chat);
    }

    //READ - 하나 조회
    public ChatEntity findChat(long chatId) {
        ChatEntity chat = verifiedChat(chatId); // chat 이 있는지 검증

        return chatRepository.save(chat);
    }

    //READ - 전체 조회
    public Page<ChatEntity> findChats(int page, int size) {
        return chatRepository.findAll(
                PageRequest.of(page, size, Sort.by("chatId").descending())
        );
    }

    //UPDATE
    public ChatEntity updateChat(long chatId, ChatEntity changedChat) {
        ChatEntity chat = verifiedChat(chatId); // chat 이 있는지 검증

        chat.setContent(changedChat.getContent());
        chat.setIsClosedChat(changedChat.getIsClosedChat());

        return chatRepository.save(chat);
    }

    //DELETE
    public void deleteChat(long chatId) {
        ChatEntity chat = verifiedChat(chatId); // chat 이 있는지 검증
        chatRepository.delete(chat);
    }

    public ChatEntity verifiedChat(long chatId) { // 해당 chatId를 사용하고 있는 채팅이 존재하는가?
        Optional<ChatEntity> chat = chatRepository.findById(chatId);
        return chat.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHAT_NOT_FOUND));
    }

    public Page<ChatEntity> findByStudy(long studyId, int page, int size) {
        Study findStudy = studyService.VerifiedStudy(studyId);
        return chatRepository.findByStudy(findStudy, PageRequest.of(page, size, Sort.by("chatId").descending()));
    }
}
