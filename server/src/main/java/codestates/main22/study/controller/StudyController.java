package codestates.main22.study.controller;

import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.study.dto.StudyDto;
import codestates.main22.study.dto.StudyNotificationDto;
import codestates.main22.study.dto.StudyRequesterDto;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/study")
@Validated
public class StudyController {
    private final StudyService studyService;
    private final StudyMapper studyMapper;

    public StudyController(StudyService studyService,StudyMapper mapper) {
        this.studyService = studyService;
        this.studyMapper = mapper;
    }

    @PostMapping //스터디 작성
    public ResponseEntity postStudy(@Valid @RequestBody StudyDto.Post requestBody) {
        Study study = studyMapper.studyPostDtoToStudy(requestBody);
        Study createStudy = studyService.createStudy(study);
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

    @GetMapping("/{study-id}/notification") //studyHall/Notification에서 공지만 확인
    public ResponseEntity getNotification(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyNotificationDto.Response response = studyMapper.studyToStudyNotificationResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/{study-id}/notification") //studyHall/Notification에서 공지만 수정
    public ResponseEntity patchNotification(@PathVariable("study-id") @Positive long studyId,
                                            @Valid @RequestBody StudyNotificationDto.Patch requestBody) {
        requestBody.setStudyId(studyId);
        Study study = studyMapper.studyNotificationPatchDtoToStudyNotification(requestBody);
        Study updateStudyNotification = studyService.updateStudy(study);
        StudyNotificationDto.Response response = studyMapper.studyToStudyNotificationResponseDto(updateStudyNotification);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/requester") //study 신청자만 보기
    public ResponseEntity getRequester(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }
}
