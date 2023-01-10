package codestates.main22.study.mapper;

import codestates.main22.study.dto.StudyDto;
import codestates.main22.study.dto.StudyMainDto;
import codestates.main22.study.dto.StudyNotificationDto;
import codestates.main22.study.dto.StudyRequesterDto;
import codestates.main22.study.entity.Study;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudyMapper {
    Study studyPostDtoToStudy(StudyDto.Post requestBody);
    Study studyPatchDtoToStudy(StudyDto.Patch requestBody);
    StudyDto.Response studyToStudyResponseDto(Study study);
    List<StudyDto.Response> studiesToStudyResponseDto(List<Study> studies);
    Study studyNotificationPatchDtoToStudyNotification(StudyNotificationDto.Patch patch); // 공지만 수정
    StudyNotificationDto.Response studyToStudyNotificationResponseDto(Study study); // 공지만 호출
    StudyNotificationDto.NoticeResponse studyToStudyNoticeResponseDto(Study study); // 공지사항 호출
    StudyRequesterDto.Response studyToStudyRequesterResponseDto(Study study); // 가입 신청자만 호출
    StudyMainDto.HeaderResponse studyToStudyHeaderResponseDto(Study study); // main - header
    StudyMainDto.MainResponse studyToStudyMainResponseDto(Study study); // main - 본문
    Study studyMainPatchDtoToStudyMain(StudyMainDto.MainResponse patch); // main - 본문 수정
}
