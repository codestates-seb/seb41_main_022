package codestates.main22.study.mapper;

import codestates.main22.study.dto.StudyDto;
import codestates.main22.study.entity.Study;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudyMapper {
    Study studyPostDtoToStudy(StudyDto.Post requestBody);
    Study studyPatchDtoToStudy(StudyDto.Patch requestBody);
    StudyDto.Response studyToStudyResponseDto(Study study);
    List<StudyDto.Response> studiesToStudyResponseDto(List<Study> studies);
}
