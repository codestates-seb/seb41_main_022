package codestates.main22.user.controller;

import codestates.main22.dto.ListResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.dto.StudyRequesterDto;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.mapper.UserMapper;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.Token;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/user")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final StudyService studyService;
    private final StudyMapper studyMapper;
    private final Token token;

    //CRUD 순서에 맞춰서

    //CREATE
    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post post) {
        UserEntity user = userService.createUser(userMapper.userPostDtoToUserEntity(post));

        UserDto.Response responseDto = userMapper.userEntityToResponseCheck(user);
        SingleResponseDto<UserDto.Response> response = new SingleResponseDto<>(responseDto);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //READ - 하나 조회 필요한 정보만 조회하도록 수정됨 (유저 식별값, 닉네임, 이미지)
    @GetMapping
    public ResponseEntity getUser(HttpServletRequest request) {
        UserEntity user = userService.findUser(request);

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        userMapper.userEntityToSearchUserResponse(user)),HttpStatus.OK);
    }

//    //READ - 전체 조회 // 사용않음 + URL 중복으로 주석처리
//    @GetMapping
//    public ResponseEntity getUsers(@Positive @RequestParam int page,
//                                   @Positive @RequestParam int size) {
//        Page<UserEntity> pageUsers = userService.findUsers(page - 1 , size);
//        List<UserEntity> users = pageUsers.getContent();
//
//        return new ResponseEntity(
//                new MultiResponseDto<>
//                        (userMapper.usersToResponse(users),pageUsers),HttpStatus.OK);
//    }

    //UPDATE // 현재 전체 프로필 수정이 없으므로 URL중복(시트 기준)으로 주석처리, 나중에 전체 프로필 수정이 필요하면 URL 수정해서 사용할 예정
//    @PatchMapping("/{user-id}")
//    public ResponseEntity patchUser(@Positive @PathVariable("user-id") long userId,
//                                    @Valid @RequestBody UserDto.Patch patch) {
//
//        UserEntity user = userService.updateUser(
//                userId, userMapper.userPatchDtoToUserEntity(patch));
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(
//                        userMapper.userEntityToResponseCheck(user)),HttpStatus.OK);
//    }

    @PatchMapping //프로필 이름만 수정
    public ResponseEntity patchUserName(HttpServletRequest request,
                                        @Valid @RequestBody UserDto.NamePatch namePatch) {
        UserEntity findUser = userService.findUser(request);
        namePatch.setUserId(findUser.getUserId());
        UserEntity user = userService.updateUser(userMapper.userNamePatchDtoToUserEntity(namePatch));
        return new ResponseEntity<>(new SingleResponseDto<>(userMapper.userEntityToNameResponse(user)),HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping
    public ResponseEntity deleteUser(HttpServletRequest request) {
        UserEntity findUser = userService.findUser(request);
        long userId = findUser.getUserId();
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/image") // 헤더에 유저 이미지만 노출
    public ResponseEntity userImage(HttpServletRequest request) {
        UserEntity findUser = userService.findUser(request);
        return new ResponseEntity<>(new SingleResponseDto<>(userMapper.userEntityToImageResponseCheck(findUser)),HttpStatus.OK);
    }

    @GetMapping("/study") //해당 스터디 구성원만 모아서 보기
    public ResponseEntity getStudyUsers(@Positive @RequestParam int studyId) {
        List<UserEntity> studyUsers = userService.findByStudy(studyId);
        return new ResponseEntity<>(
                new ListResponseDto<>(userMapper.usersToStudyUserResponse(studyUsers, String.valueOf(studyId))),
                HttpStatus.OK
        );
    }

    @GetMapping("/{study-id}/requester") //스터디 신청자만 모아서 보기
    public ResponseEntity getRequester(@PathVariable("study-id") @Positive long studyId,
                                       HttpServletRequest request) {

        Study findStudy = studyService.findStudy(studyId);
        UserEntity loginUser = token.findByToken(request);

        if (findStudy.getLeaderId() != loginUser.getUserId()) {
            throw new BusinessLogicException(ExceptionCode.NO_AUTHORITY);
        }

        StudyRequesterDto.Response userList = studyMapper.studyToStudyRequesterResponseDto(findStudy);
        List<UserEntity> requesterList = userService.findRequester(userList);
        return new ResponseEntity<>(new SingleResponseDto<>(userMapper.userEntityToSearchUsersResponse(requesterList)), HttpStatus.OK);
    }
}
