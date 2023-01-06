package codestates.main22.answer.service;

import codestates.main22.answer.entity.AnswerEntity;
import codestates.main22.answer.repository.AnswerRepository;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AnswerService {
    private AnswerRepository answerRepository;

    //CRUD 순서에 맞춰서

    //CREATE
    public AnswerEntity createAnswer(AnswerEntity answer) {
        return answerRepository.save(answer);
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
