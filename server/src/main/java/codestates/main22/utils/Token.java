package codestates.main22.utils;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
public class Token {
    private final UserRepository userRepository;

    Token(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // refresh-Token을 이용해서 user 정보 조회하기
    public UserEntity findByToken(HttpServletRequest request) {
        String refresh = request.getHeader("refresh-token");
        Optional<UserEntity> refreshUser = userRepository.findTop1ByRefresh(refresh);
        return refreshUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    // refresh-Token을 이용해서 user 여부 확인
    public boolean checkUserByToken(HttpServletRequest request) {
        String refresh = request.getHeader("refresh-token");
        Optional<UserEntity> user = userRepository.findTop1ByRefresh(refresh);

        if(user.orElse(new UserEntity()).getUsername() == null)
            return false;

        return true;
    }


    // 스터디장 권한이 있는지 확인
    public UserEntity checkStudyAdmin(HttpServletRequest request, long studyId){
        UserEntity user = findByToken(request);
        if(!user.getRole().contains("STUDY" + studyId + "_ADMIN")) // 권한이 없으면 에러 발생
            throw new BusinessLogicException(ExceptionCode.NOT_AN_ADMINISTRATOR);

        return user;
    }

    // 스터디 가입 여부 확인
    public UserEntity checkStudy(HttpServletRequest request, long studyId){
        UserEntity user = findByToken(request);

        // 권한이 없으면 에러 발생
        if(!user.getRole().contains("STUDY" + studyId + "_ADMIN") && !user.getRole().contains("STUDY" + studyId + "_USER"))
            throw new BusinessLogicException(ExceptionCode.NOT_JOIN_STUDY);

        return user;
    }
}
