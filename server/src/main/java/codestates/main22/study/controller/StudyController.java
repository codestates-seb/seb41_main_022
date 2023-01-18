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
    private final UserRepository userRepository;

    @PostMapping // #38 - 스터디 작성 'Create New Study'
    public ResponseEntity postStudy(@Valid @RequestBody StudyDto.Post requestBody,
                                    HttpServletRequest request) {
        Study study = studyMapper.studyPostDtoToStudy(requestBody);
        Study createStudy = studyService.createStudy(study, request);
        List<String> tags = studyService.createTagStudies(createStudy, requestBody.getTags());
        StudyDto.ResponseTag response = studyMapper.studyToStudyResponseDto(createStudy, tags);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    @PatchMapping("/{study-id}") // 스터디 수정 (기본 CRUD)
    public ResponseEntity patchStudy(@PathVariable("study-id") @Positive long studyId,
                                     @Valid @RequestBody StudyDto.Patch requestBody) {
        requestBody.setStudyId(studyId);
        Study study = studyMapper.studyPatchDtoToStudy(requestBody);
        Study updateStudy = studyService.updateStudy(study);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(updateStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}") // (postman 디버깅용) 특정 스터디 조회 (기본 CRUD)
    public ResponseEntity getStudy(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping // (postman 디버깅용) 스터디 전체 조회 (아무런 필터링 없는 기본 CRUD)
    public ResponseEntity getStudies(@RequestParam int page,
                                     @RequestParam int size) {
        Page<Study> pageStudies = studyService.findStudies(page-1, size);
        List<Study> studies = pageStudies.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
    }

    @GetMapping("/first-cards") // #6 - 스터디 전체 조회 (openClose 기준으로) - 처음 조회했을 경우
    public ResponseEntity getStudiesByOpenClose(@RequestParam int page,
                                                @RequestParam int size) {

        Page<Study> pageStudies = studyService.findStudiesByFilters(page-1, size);
        List<Study> studies = pageStudies.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
    }

    @GetMapping("/cards") // #7 - 스터디 전체 조회 (tag 기준 필터링)
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

    @DeleteMapping("/{study-id}") // #21 - 스터디 삭제 (방장 권한으로)
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

    @DeleteMapping("/{study-id}/{user-id}") // #22 - 스터디 탈퇴 (멤버인 경우에만)
    public ResponseEntity withdrawStudy(@PathVariable("study-id") @Positive long studyId,
                                        @PathVariable("user-id") int userId) {
        Study findStudy = studyService.findStudy(studyId);

        if(!studyService.isMember(userId, studyId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }

        studyService.removeUserAuth(findStudy, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{study-id}/notification") // #17 - studyHall/Notification 에서 공지만 확인
    public ResponseEntity getNotification(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyNotificationDto.Response response = studyMapper.studyToStudyNotificationResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PatchMapping("/{study-id}/notification") // #18 - studyHall/Notification 에서 공지만 수정
    public ResponseEntity patchNotification(@PathVariable("study-id") @Positive long studyId,
                                            @Valid @RequestBody StudyNotificationDto.Patch patch) {

        Study study = studyService.updateStudyNotice(
                studyId, studyMapper.studyNotificationPatchDtoToStudyNotification(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        studyMapper.studyToStudyNotificationResponseDto(study)), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/notice") // #28 - studyHall/main 에서 공지사항 확인
    public ResponseEntity getNotice(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyNotificationDto.NoticeResponse response = studyMapper.studyToStudyNoticeResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @PostMapping("/{study-id}/requester") // #37 - main 스터디 신청 : 버튼이 이미 활성화 되어 있다 가정
    public ResponseEntity registerStudy(@PathVariable("study-id") @Positive long studyId,
                                        HttpServletRequest request) {

        Study findStudy = studyService.findStudy(studyId);
        UserEntity loginUser = userRepository.findByToken(request);

        studyService.addRequester(findStudy, loginUser.getUserId());

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PostMapping("/{study-id}/requester/{user-id}/accept") // #24 - main 스터디 신청 수락
    public ResponseEntity acceptRegisterStudy(@PathVariable("study-id") @Positive long studyId,
                                              @PathVariable("user-id") @Positive long userId) {

        Study findStudy = studyService.findStudy(studyId);

        studyService.removeRequester(findStudy, userId);
        studyService.giveUserAuth(findStudy, userId);

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PostMapping("/{study-id}/requester/{user-id}/reject") // #25 - main 스터디 신청 거절
    public ResponseEntity rejectRegisterStudy(@PathVariable("study-id") @Positive long studyId,
                                              @PathVariable("user-id") @Positive long userId) {

        Study findStudy = studyService.findStudy(studyId);

        studyService.removeRequester(findStudy, userId);

        StudyRequesterDto.Response response = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @GetMapping("/{study-id}/requester") // (postman 디버그용) - study 신청자만 보기
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

    @GetMapping("/{study-id}/auth") // #26 - 각종 true false 변수들 넘겨주기 (token 값을 사용)
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

    @GetMapping("/{study-id}/user/{user-id}/auth") // #26 - 각종 true false 변수들 넘겨주기 (token 값 사용 X)
    public ResponseEntity getAuthWithUserId(@PathVariable("study-id") @Positive long studyId,
                                            @PathVariable("user-id") @Positive long userId) {

        Study findStudy = studyService.findStudy(studyId);

        Boolean checkMember = studyService.isMember(userId, studyId);
        Boolean checkHost = findStudy.getLeaderId() == userId;
        Boolean checkRequest = findStudy.getRequester().contains(userId);

        StudyMainDto.AuthResponse response =
                studyMapper.studyToStudyAuthResponseDto(findStudy, checkMember, checkHost, checkRequest);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/header") // #27 - studyHall/main 윗부분 header
    public ResponseEntity getMainHeader(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyMainDto.HeaderResponse response = studyMapper.studyToStudyHeaderResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}/main") // #29 - studyHall/main 본문
    public ResponseEntity getMainBody(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        List<String> tags = studyService.findTagsByStudy(findStudy);
        StudyMainDto.MainResponse response = studyMapper.studyToStudyMainResponseDto(findStudy, tags);
        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @PatchMapping("/{study-id}/main") // #31 - studyHall/main 본문 수정
    public ResponseEntity patchMainBody(@PathVariable("study-id") @Positive long studyId,
                                            @Valid @RequestBody StudyMainDto.MainResponse patch) {

        Study study = studyService.updateMainBody(
                studyId, studyMapper.studyMainPatchDtoToStudyMain(patch));
        List<String> tags = studyService.updateTag(study, patch.getTags());

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        studyMapper.studyToStudyMainResponseDto(study, tags)),HttpStatus.OK
        );
    }

    // #9 - user의 study 조회
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
