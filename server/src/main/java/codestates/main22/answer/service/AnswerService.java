package codestates.main22.answer.service;

import codestates.main22.answer.entity.AnswerEntity;
import codestates.main22.answer.repository.AnswerRepository;
import codestates.main22.chat.entity.ChatEntity;
import codestates.main22.chat.service.ChatService;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerService {
    private AnswerRepository answerRepository;
    private ChatService chatService;
    private final UserRepository userRepository;

    //CRUD 순서에 맞춰서

    //CREATE
    public AnswerEntity createAnswer(long chatId, AnswerEntity answer, HttpServletRequest request) {
        ChatEntity findChat = chatService.findChat(chatId);
        answer.setChat(findChat);

        UserEntity user = userRepository.findByToken(request);
        Study findChatStudy = findChat.getStudy(); // 채팅이 어느 스터디 있는지 찾고
        long studyId = findChatStudy.getStudyId(); // 찾은 스터디의 식별값을 검색합니다. -> 스터디의 권한을 검색하기 위해
        String admin = "STUDY" + studyId + "_ADMIN"; // 스터디장이라면 허용

        boolean makeUser = false;
        if(!(findChat.getChatUserId() == user.getUserId())){makeUser = true;} // 채팅을 작성한 유저와 현재 유저가 같은 유저인지 확인
        if(!(user.getRole().contains(admin) || makeUser)) {new BusinessLogicException(ExceptionCode.NOT_AN_ADMINISTRATOR);} // 스터디장 or 채팅 작성자만 허용

        answerRepository.save(answer);
        return answer;
    }

    //READ - 하나 조회
    public AnswerEntity findAnswer(long answerId) {
        AnswerEntity answer = verifiedAnswer(answerId); // answer 가 있는지 검증

        return answerRepository.save(answer);
    }

    //READ - 전체 조회
    public Page<AnswerEntity> findAnswers(int page, int size) {
        return answerRepository.findAll(
                PageRequest.of(page, size, Sort.by("answerId").descending())
        );
    }

    //UPDATE
    public AnswerEntity updateAnswer(long answerId, AnswerEntity changedAnswer) {
        AnswerEntity answer = verifiedAnswer(answerId); // answer 가 있는지 검증

        answer.setContent(changedAnswer.getContent());
        answer.setIsClosedChat(changedAnswer.getIsClosedChat());

        return answerRepository.save(answer);
    }

    //DELETE
    public void deleteAnswer(long answerId) {
        AnswerEntity answer = verifiedAnswer(answerId); // answer 가 있는지 검증

        answerRepository.delete(answer);
    }

    public AnswerEntity verifiedAnswer(long answerId) { // 해당 answerId를 사용하고 있는 답변이 존재하는가?
        Optional<AnswerEntity> answer = answerRepository.findById(answerId);
        return answer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }
}
