package codestates.main22.study.mapper;

import codestates.main22.study.dto.*;
import codestates.main22.study.entity.Study;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudyMapper {
    Study studyPostDtoToStudy(StudyDto.Post requestBody);
    Study studyPatchDtoToStudy(StudyDto.Patch requestBody);
    StudyDto.Response studyToStudyResponseDto(Study study);
    StudyDto.ResponseTag studyToStudyResponseDto(Study study, List<String> tags);
    List<StudyDto.Response> studiesToStudyResponseDto(List<Study> studies);
    List<StudyDto.CardResponse> studiesToStudyCardResponseDto(List<Study> studies);
    Study studyNotificationPatchDtoToStudyNotification(StudyNotificationDto.Patch patch); // 공지만 수정
    StudyNotificationDto.Response studyToStudyNotificationResponseDto(Study study); // 공지만 호출
    StudyNotificationDto.NoticeResponse studyToStudyNoticeResponseDto(Study study); // 공지사항 호출
    StudyRequesterDto.Response studyToStudyRequesterResponseDto(Study study); // 가입 신청자만 호출
    StudyMainDto.HeaderResponse studyToStudyHeaderResponseDto(Study study); // main - header

    // 권한 관련 변수 넘겨주기
    default StudyMainDto.AuthResponse studyToStudyAuthResponseDto(
            Study study, Boolean checkMember, Boolean checkHost, Boolean checkRequest) {
        if (study == null) {
            return null;
        }
        boolean isMember = checkMember;
        boolean isHost = checkHost;
        boolean isRequest = checkRequest;

        StudyMainDto.AuthResponse response =
                new StudyMainDto.AuthResponse(isMember, isHost, isRequest);
        return response;
    }
    StudyMainDto.MainResponse studyToStudyMainResponseDto(Study study, List<String> tags); // main - 본문
    StudyMainDto.MainPatch studyToStudyMainPatchResponseDto(Study study, List<String> tags); // main - 본문 수정
    Study studyMainPatchDtoToStudyMain(StudyMainDto.MainPatch patch); // main - 본문 수정

    List<StudyUserDto.Studys> studiesToStudyUserDto(List<Study> studies); // user
}
