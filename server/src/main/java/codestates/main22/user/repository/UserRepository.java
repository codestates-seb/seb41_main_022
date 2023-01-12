package codestates.main22.user.repository;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
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

//    List<UserEntity> findByRole(List<String> role);
//
//    default UserEntity findByRole(Study studyId) {}

}
