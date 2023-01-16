package codestates.main22.study.controller;

import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.dto.*;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
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
    private final UserRepository userRepository;

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

    @GetMapping("/first-cards") //스터디 전체 조회 (openClose 기준으로) - 처음 조회했을 경우
    public ResponseEntity getStudiesByOpenClose(@RequestParam int page,
                                                @RequestParam int size) {

        Page<Study> pageStudies = studyService.findStudiesByFilters(page-1, size);
        List<Study> studies = pageStudies.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
    }

    @GetMapping("/cards") //스터디 전체 조회 (openClose 기준으로)
    public ResponseEntity getStudiesByOpenClose(@RequestParam int page,
                                                @RequestParam int size,
                                                @RequestParam String search,
                                                @RequestParam String filter,
                                                @RequestParam List<String> tags) {

        Page<Study> pageStudies = studyService.findStudiesByFilters(page-1, size, search, filter, tags);
        List<Study> studies = pageStudies.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
    }

//    @PostMapping("/cards/tag")
//    public ResponseEntity getStudiesByTag(@RequestParam int page,
//                                          @RequestParam int size,
//                                          @RequestBody List<String> tags) {
//        Page<Study> pageStudies = studyService.findStudiesByTag(page-1, size, tags);
//        List<Study> studies = pageStudies.getContent();
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
//    }

    @DeleteMapping("/{study-id}") //스터디 삭제 (방장 권한으로)
    public ResponseEntity deleteStudy(@PathVariable("study-id") @Positive long studyId,
                                      HttpServletRequest request) {
        Study findStudy = studyService.findStudy(studyId);
        UserEntity loginUser = userRepository.findByToken(request);

        if(findStudy.getLeaderId() != loginUser.getUserId()) {
            throw new BusinessLogicException(ExceptionCode.NO_AUTHORITY);
        }

        studyService.deleteStudy(studyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{study-id}/{user-id}") //스터디 탈퇴 (멤버인 경우에만)
    public ResponseEntity withdrawStudy(@PathVariable("study-id") @Positive long studyId,
                                        @PathVariable("user-id") int userId) {
        Study findStudy = studyService.findStudy(studyId);

        if(!studyService.isMember(userId, studyId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }

        studyService.removeUserAuth(findStudy, userId);
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
                        studyMapper.studyToStudyNotificationResponseDto(study)), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/notice") //studyHall/main 에서 공지사항 확인
    public ResponseEntity getNotice(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyNotificationDto.NoticeResponse response = studyMapper.studyToStudyNoticeResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/{study-id}/requester") // main 스터디 신청 : 버튼이 이미 활성화 되어 있다 가정
    public ResponseEntity registerStudy(@PathVariable("study-id") @Positive long studyId,
                                        HttpServletRequest request) {

        Study findStudy = studyService.findStudy(studyId);
        UserEntity loginUser = userRepository.findByToken(request);

        studyService.addRequester(findStudy, loginUser.getUserId());

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PostMapping("/{study-id}/requester/{user-id}/accept") // main 스터디 신청 수락
    public ResponseEntity acceptRegisterStudy(@PathVariable("study-id") @Positive long studyId,
                                              @PathVariable("user-id") @Positive long userId) {

        Study findStudy = studyService.findStudy(studyId);

        studyService.removeRequester(findStudy, userId);
        studyService.giveUserAuth(findStudy, userId);

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PostMapping("/{study-id}/requester/{user-id}/reject") // main 스터디 신청 거절
    public ResponseEntity rejectRegisterStudy(@PathVariable("study-id") @Positive long studyId,
                                              @PathVariable("user-id") @Positive long userId) {

        Study findStudy = studyService.findStudy(studyId);

        studyService.removeRequester(findStudy, userId);

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @GetMapping("/{study-id}/requester") //study 신청자만 보기
    public ResponseEntity getRequester(@PathVariable("study-id") @Positive long studyId,
                                       HttpServletRequest request) {

        Study findStudy = studyService.findStudy(studyId);
        UserEntity loginUser = userRepository.findByToken(request);

        if (findStudy.getLeaderId() != loginUser.getUserId()) {
            throw new BusinessLogicException(ExceptionCode.NO_AUTHORITY);
        }

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/auth") // 각종 true false 변수들 넘겨주기
    public ResponseEntity getAuth(@PathVariable("study-id") @Positive long studyId,
                                  HttpServletRequest request) {

        Study findStudy = studyService.findStudy(studyId);
        UserEntity loginUser = userRepository.findByToken(request);

        Boolean checkMember = studyService.isMember(loginUser.getUserId(), studyId);
        Boolean checkHost = findStudy.getLeaderId() == loginUser.getUserId();
        Boolean checkRequest = findStudy.getRequester().contains(loginUser.getUserId());

        StudyMainDto.AuthResponse response =
                studyMapper.studyToStudyAuthResponseDto(findStudy, checkMember, checkHost, checkRequest);
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

    // user의 study 조회
    @GetMapping("/user")
    public ResponseEntity getStudiesByUser(HttpServletRequest request) {
        List<Study> studies = studyService.findStudiesByUser(request);
        List<StudyUserDto.Studys> response = studyMapper.studiesToStudyUserDto(studies);

        return new ResponseEntity<>(
                new SingleResponseDto(new StudyUserDto.Response(response.size(), response)),
                HttpStatus.OK
        );
    }

}
