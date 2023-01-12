package codestates.main22.answer.controller;

import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.AnswerEntity;
import codestates.main22.answer.mapper.AnswerMapper;
import codestates.main22.answer.service.AnswerService;
import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
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

    //CRUD 순서에 맞춰서

    //CREATE
    @PostMapping("/{chat-id}")
    public ResponseEntity postAnswer(@Positive @PathVariable("chat-id") long chatId,
                                     @Valid @RequestBody AnswerDto.Post post,
                                     HttpServletRequest request) {
        AnswerEntity answer = answerService.createAnswer(chatId, answerMapper.answerPostDtoToAnswerEntity(post), request);

        AnswerDto.Response responseDto = answerMapper.answerEntityToResponseCheck(answer);
        SingleResponseDto<AnswerDto.Response> response = new SingleResponseDto<>(responseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //READ - 하나 조회
    @GetMapping("/{answer-id}")
    public ResponseEntity getAnswer(@Positive @PathVariable("answer-id") long answerId) {
        AnswerEntity answer = answerService.findAnswer(answerId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        answerMapper.answerEntityToResponseCheck(answer)),HttpStatus.OK);
    }

    //READ - 전체 조회
    @GetMapping
    public ResponseEntity getAnswers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<AnswerEntity> pageAnswers = answerService.findAnswers(page - 1 , size);
        List<AnswerEntity> answers = pageAnswers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(
                        answerMapper.answersToResponse(answers),pageAnswers),HttpStatus.OK);
    }

    //UPDATE
    @PatchMapping("/{answer-id}")
    public ResponseEntity patchAnswer(@Positive @PathVariable("answer-id") long answerId,
                                      @Valid @RequestBody AnswerDto.Patch patch) {
        AnswerEntity answer = answerService.updateAnswer(
                answerId, answerMapper.answerPatchDtoToAnswerEntity(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        answerMapper.answerEntityToResponseCheck(answer)),HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/{answer-id}")
    public ResponseEntity deleteAnswer(@PathVariable("answer-id") @Positive long answerId) {
        answerService.deleteAnswer(answerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
