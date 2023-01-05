package codestates.main22.dm.repository;

import codestates.main22.dm.entity.DmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DmRepository extends JpaRepository<DmEntity,Long> {
}
