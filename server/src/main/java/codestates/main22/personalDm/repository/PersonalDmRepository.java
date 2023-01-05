package codestates.main22.personalDm.repository;

import codestates.main22.personalDm.entity.PersonalDmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalDmRepository extends JpaRepository<PersonalDmEntity,Long> {
}
