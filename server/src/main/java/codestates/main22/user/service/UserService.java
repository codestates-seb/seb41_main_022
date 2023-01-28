package codestates.main22.user.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.dto.StudyRequesterDto;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.user.repository.UserStudyRepository;
import codestates.main22.utils.Token;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserStudyRepository userStudyRepository;
    private final StudyService studyService;
    private final StudyRepository studyRepository;

    private final CustomAuthorityUtils authorityUtils;
    private final Token token;

    //CRUD 순서에 맞춰서

    //CREATE
    public UserEntity createUser(UserEntity user) {
        // DB에 User Role 저장
        List<String> roles = authorityUtils.createRoles(user.getEmail());
        user.setRole(roles);

        return userRepository.save(user);
    }

    //READ - 하나 조회
    public UserEntity findUser(HttpServletRequest request) {
        UserEntity user = token.findByToken(request); // user 가 있는지 검증

        return userRepository.save(user);
    }

    //READ - 전체 조회
    public Page<UserEntity> findUsers(int page, int size) {
        return userRepository.findAll(
                PageRequest.of(page, size, Sort.by("userId").descending())
        );
    }

    //UPDATE
    public UserEntity updateUser(UserEntity changedUser) {
        UserEntity findUser = verifiedUser(changedUser.getUserId());
        Optional.ofNullable(changedUser.getUsername()).ifPresent(findUser::setUsername);
        Optional.ofNullable(changedUser.getEmail()).ifPresent(findUser::setEmail);
        Optional.ofNullable(changedUser.getInfo()).ifPresent(findUser::setInfo);
        Optional.ofNullable(changedUser.getImgUrl()).ifPresent(findUser::setImgUrl);
        Optional.ofNullable(changedUser.getToken()).ifPresent(findUser::setToken);
        Optional.ofNullable(changedUser.getRefresh()).ifPresent(findUser::setRefresh);
//        user.setUsername(changedUser.getUsername());
//        user.setEmail(changedUser.getEmail());
//        user.setInfo(changedUser.getInfo());
//        user.setImgUrl(changedUser.getImgUrl());

        return userRepository.save(findUser);
    }

    //DELETE
    public void deleteUser(long userId) {
        UserEntity user = verifiedUser(userId); // user 가 있는지 검증

        userRepository.delete(user);
    }


    public UserEntity verifiedUser(long userId) { // 해당 userId를 사용하고 있는 유저가 존재하는가?
        Optional<UserEntity> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }


    // 스터디 아이디 찾기 (스터디별 구성원 보기 기능에 사용)
    public List<UserEntity> findByStudy(long studyId) {
        Study findStudy = studyService.VerifiedStudy(studyId);
        List<UserEntity> userList =  userStudyRepository.findByStudy(findStudy).stream().map(userStudyEntity -> userStudyEntity.getUser())
                .collect(Collectors.toList());
        return userList;
    }

    public List<Study> findStudiesByUser(UserEntity user) {
        return studyRepository.findByUserStudiesUser(user);
    }

    // access-Token을 이용해서 user 정보 조회하기
    public UserEntity findByToken(HttpServletRequest request) {
        return token.findByToken(request);
    }

    // email로 user 정보 조회
    public UserEntity findByEmail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findTop1ByEmail(email);
        UserEntity user = optionalUser.orElse(null);

        return user;
    }

    public List<UserEntity> findRequester(StudyRequesterDto.Response userList) {
        List<UserEntity> requester = new ArrayList<>();
        List<Long> list = userList.getRequester();
        for(int i=0; i<list.size(); i++) {
            UserEntity user = verifiedUser(list.get(i));
            requester.add(user);
        }
        return requester;
    }

    public Optional<UserEntity> findByToken(String token) {
        return userRepository.findTop1ByToken(token);
    }
}
