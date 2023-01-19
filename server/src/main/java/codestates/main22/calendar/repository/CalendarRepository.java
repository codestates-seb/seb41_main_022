package codestates.main22.calendar.repository;

import codestates.main22.calendar.entity.Calendar;
import codestates.main22.study.entity.Study;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByStudy(Study study);
}