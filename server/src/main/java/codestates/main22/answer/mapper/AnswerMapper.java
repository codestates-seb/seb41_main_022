package codestates.main22.answer.mapper;

import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.Answer;
import codestates.main22.user.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    Answer answerPostDtoToAnswer(AnswerDto.Post post);
    Answer answerPatchDtoToAnswer(AnswerDto.Patch patch);
    AnswerDto.Response answerToResponseCheck(Answer answer, UserEntity user);
    List<AnswerDto.Response> answersToResponse(List<Answer> answers);
}
