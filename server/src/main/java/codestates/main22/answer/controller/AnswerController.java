package codestates.main22.answer.controller;

import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.Answer;
import codestates.main22.answer.mapper.AnswerMapper;
import codestates.main22.answer.service.AnswerService;
import codestates.main22.chat.entity.Chat;
import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.utils.Token;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/answer")
@Validated
@AllArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final Token token;

    //CRUD 순서에 맞춰서

    // studyHall/main 댓글 작성(아래)
    @PostMapping("/{chat-id}")
    public ResponseEntity postAnswer(@Positive @PathVariable("chat-id") long chatId,
                                     @Valid @RequestBody AnswerDto.Post post,
                                     HttpServletRequest request) {
        UserEntity user = answerService.findUserByToken(request);
        Answer answer = answerService.createAnswer(chatId, answerMapper.answerPostDtoToAnswer(post), user);

        AnswerDto.Response response = answerMapper.answerToResponseCheck(answer, user);
        response.setAnswerCreatedAt(answer.getCreatedAt());

        return new ResponseEntity<>(new SingleResponseDto(response), HttpStatus.CREATED);
    }


    //READ - 하나 조회
//    @GetMapping("/{answer-id}")
//    public ResponseEntity getAnswer(@Positive @PathVariable("answer-id") long answerId) {
//        Answer answer = answerService.findAnswer(answerId);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(
//                        answerMapper.answerToResponseCheck(answer)),HttpStatus.OK);
//    }

    //READ - 전체 조회
    @GetMapping
    public ResponseEntity getAnswers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Answer> pageAnswers = answerService.findAnswers(page - 1 , size);
        List<Answer> answers = pageAnswers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(
                        answerMapper.answersToResponse(answers),pageAnswers),HttpStatus.OK);
    }


    //DELETE
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteChat(@PathVariable("answer-id") @Positive long answerId,
                                     HttpServletRequest request) {
        UserEntity loginUser = token.findByToken(request);
        long userId = loginUser.getUserId();

        Answer answer = answerService.findAnswer(answerId);
        long answerUserId = answer.getAnswerUserId();
        if(userId != answerUserId) {
            throw new BusinessLogicException(ExceptionCode.NO_AUTHOR);
        }
        answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //UPDATE
//    @PatchMapping("/{answer-id}")
//    public ResponseEntity patchAnswer(@Positive @PathVariable("answer-id") long answerId,
//                                      @Valid @RequestBody AnswerDto.Patch patch) {
//        Answer answer = answerService.updateAnswer(answerId, answerMapper.answerPatchDtoToAnswer(patch));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(
//                        answerMapper.answerToResponseCheck(answer)),HttpStatus.OK);
//    }

}
