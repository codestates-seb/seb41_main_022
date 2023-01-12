package codestates.main22.user.repository;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByToken(String token);

    // access-Token을 이용해서 user 정보 조회하기
    default UserEntity findByToken(HttpServletRequest request) {
        String token = request.getHeader("access-Token");
        Optional<UserEntity> user = findByToken(token);
        return user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

    // access-Token을 이용해서 user 여부 확인
    default boolean checkUserByToken(HttpServletRequest request) {
        String token = request.getHeader("access-Token");
        Optional<UserEntity> user = findByToken(token);

        if(user.orElse(new UserEntity()).getUsername() == null)
            return false;

        return true;
    }


    // 스터디장 권한이 있는지 확인
    default UserEntity checkStudyAdmin(HttpServletRequest request, long studyId){
        UserEntity user = findByToken(request);
        if(!user.getRole().contains("STUDY" + studyId + "_ADMIN")) // 권한이 없으면 에러 발생
            throw new BusinessLogicException(ExceptionCode.NOT_AN_ADMINISTRATOR);

        return user;
    }

    // 스터디 가입 여부 확인
    default UserEntity checkStudy(HttpServletRequest request, long studyId){
        UserEntity user = findByToken(request);

        // 권한이 없으면 에러 발생
        if(!user.getRole().contains("STUDY" + studyId + "_ADMIN") && !user.getRole().contains("STUDY" + studyId + "_USER"))
            throw new BusinessLogicException(ExceptionCode.NOT_JOIN_STUDY);

        return user;
    }
}
