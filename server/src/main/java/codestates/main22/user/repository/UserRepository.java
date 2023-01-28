package codestates.main22.user.repository;

import codestates.main22.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByToken(String token);
    Optional<UserEntity> findByRefresh(String Refresh);
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByUserId(long userId);
}
