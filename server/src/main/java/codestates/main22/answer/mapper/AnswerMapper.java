package codestates.main22.answer.mapper;

import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.AnswerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerEntity answerPostDtoToAnswerEntity(AnswerDto.Post post);
    AnswerEntity answerPatchDtoToAnswerEntity(AnswerDto.Patch patch);
    AnswerDto.Response answerEntityToResponseCheck(AnswerEntity answer);
    List<AnswerDto.Response> answersToResponse(List<AnswerEntity> answers);
}
