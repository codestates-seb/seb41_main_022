package codestates.main22.study.controller;

import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.study.dto.StudyDto;
import codestates.main22.study.dto.StudyMainDto;
import codestates.main22.study.dto.StudyNotificationDto;
import codestates.main22.study.dto.StudyRequesterDto;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.service.UserService;
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
@RequestMapping("/study")
@Validated
@AllArgsConstructor
public class StudyController {
    private final StudyService studyService;
    private final StudyMapper studyMapper;
    private final UserService userService;

    @PostMapping // 스터디 작성 'Create New Study'
    public ResponseEntity postStudy(@Valid @RequestBody StudyDto.Post requestBody,
                                    HttpServletRequest request) {
        Study study = studyMapper.studyPostDtoToStudy(requestBody);
        Study createStudy = studyService.createStudy(study, request);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(createStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{study-id}") //스터디 수정
    public ResponseEntity patchStudy(@PathVariable("study-id") @Positive long studyId,
                                     @Valid @RequestBody StudyDto.Patch requestBody) {
        requestBody.setStudyId(studyId);
        Study study = studyMapper.studyPatchDtoToStudy(requestBody);
        Study updateStudy = studyService.updateStudy(study);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(updateStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}") //특정 스터디 조회
    public ResponseEntity getStudy(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping //스터디 전체 조회
    public ResponseEntity getStudies(@RequestParam int page,
                                     @RequestParam int size) {
        Page<Study> pageStudies = studyService.findStudies(page-1, size);
        List<Study> studies = pageStudies.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
    }

    @DeleteMapping("/{study-id}") //스터디 삭제
    public ResponseEntity deleteStudy(@PathVariable("study-id") @Positive long studyId) {
        studyService.deleteStudy(studyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{study-id}/notification") //studyHall/Notification 에서 공지만 확인
    public ResponseEntity getNotification(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyNotificationDto.Response response = studyMapper.studyToStudyNotificationResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/{study-id}/notification") //studyHall/Notification 에서 공지만 수정
    public ResponseEntity patchNotification(@PathVariable("study-id") @Positive long studyId,
                                            @Valid @RequestBody StudyNotificationDto.Patch patch) {

        Study study = studyService.updateStudyNotice(
                studyId, studyMapper.studyNotificationPatchDtoToStudyNotification(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        studyMapper.studyToStudyNotificationResponseDto(study)),HttpStatus.OK);
    }

    @GetMapping("/{study-id}/notice") //studyHall/main 에서 공지사항 확인
    public ResponseEntity getNotice(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyNotificationDto.NoticeResponse response = studyMapper.studyToStudyNoticeResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/requester") //study 신청자만 보기
    public ResponseEntity getRequester(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/header") //studyHall/main 윗부분 header
    public ResponseEntity getMainHeader(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyMainDto.HeaderResponse response = studyMapper.studyToStudyHeaderResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/main") //studyHall/main 본문
    public ResponseEntity getMainBody(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyMainDto.MainResponse response = studyMapper.studyToStudyMainResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/{study-id}/main") //studyHall/main 본문 수정
    public ResponseEntity patchMainBody(@PathVariable("study-id") @Positive long studyId,
                                            @Valid @RequestBody StudyMainDto.MainResponse patch) {

        Study study = studyService.updateMainBody(
                studyId, studyMapper.studyMainPatchDtoToStudyMain(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        studyMapper.studyToStudyMainResponseDto(study)),HttpStatus.OK);
    }

    // TODO 로직 자체는 구현되었으나 아직 신청 -> 확인 -> 실제 스터디 가입 과정이 없기 때문에 테스트 X
    @GetMapping("/user/{user-id}") //user-id를 사용해서 study 조회
    public ResponseEntity getStudyByUserId(@PathVariable("user-id") @Positive long userId) {
        UserEntity user = userService.findUser(userId);
        List<Study> studies = userService.findStudiesByUser(user);

        return new ResponseEntity<>(new SingleResponseDto<>(studies), HttpStatus.OK);
    }
}
