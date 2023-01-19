package codestates.main22.answer.service;

import codestates.main22.answer.entity.Answer;
import codestates.main22.answer.repository.AnswerRepository;
import codestates.main22.chat.entity.Chat;
import codestates.main22.chat.service.ChatService;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.utils.Token;
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
    private final Token token;

    //CRUD 순서에 맞춰서

    //CREATE
    public Answer createAnswer(long chatId, Answer answer, UserEntity user) {
        Chat findChat = chatService.findChat(chatId);
        answer.setAnswerUserId(user.getUserId());
        answer.setChat(findChat);

        return answerRepository.save(answer);
    }

    public UserEntity findUserByToken(HttpServletRequest request) {
        return token.findByToken(request);
    }

    //READ - 하나 조회
    public Answer findAnswer(long answerId) {
        Answer answer = verifiedAnswer(answerId); // answer 가 있는지 검증

        return answerRepository.save(answer);
    }

    //READ - 전체 조회
    public Page<Answer> findAnswers(int page, int size) {
        return answerRepository.findAll(
                PageRequest.of(page, size, Sort.by("answerId").descending())
        );
    }

    //UPDATE
    public Answer updateAnswer(long answerId, Answer changedAnswer) {
        Answer answer = verifiedAnswer(answerId); // answer 가 있는지 검증

        answer.setContent(changedAnswer.getContent());

        return answerRepository.save(answer);
    }

    //DELETE
    public void deleteAnswer(long answerId) {
        Answer answer = verifiedAnswer(answerId); // answer 가 있는지 검증

        answerRepository.delete(answer);
    }

    public Answer verifiedAnswer(long answerId) { // 해당 answerId를 사용하고 있는 답변이 존재하는가?
        Optional<Answer> answer = answerRepository.findById(answerId);
        return answer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));
    }
}
