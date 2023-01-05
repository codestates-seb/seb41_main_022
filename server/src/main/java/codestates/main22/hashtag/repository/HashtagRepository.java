package codestates.main22.hashtag.repository;

import codestates.main22.hashtag.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity, Long> {
}