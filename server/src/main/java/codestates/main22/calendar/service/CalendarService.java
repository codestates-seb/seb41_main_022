package codestates.main22.calendar.service;

import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.repository.CalendarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 캘린더 생성
    public Calendar createCalendar(Calendar calendar) {
        return new Calendar();
    }
}
