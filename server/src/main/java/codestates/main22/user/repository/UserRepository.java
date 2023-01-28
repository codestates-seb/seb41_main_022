package codestates.main22.user.repository;

import codestates.main22.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findTop1ByToken(String token);
    Optional<UserEntity> findTop1ByRefresh(String Refresh);
    Optional<UserEntity> findTop1ByEmail(String email);
    UserEntity findTop1ByUserId(long userId);
}
