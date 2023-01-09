package codestates.main22.calendar.service;

import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.repository.CalendarRepository;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 캘린더 생성
    public Calendar createCalendar(Calendar calendar) {
        Map<Long, String> participant = new HashMap<>();
        participant.put(1L, Calendar.attendance.NONE.name());
        calendar.setParticipant(participant);

        return calendarRepository.save(calendar);
    }

    // 캘린더 수정
    public Calendar updateCalendar(Calendar calendar) {
        Calendar findCalendar = verifiedCalendar(calendar.getCalendarId());

        Optional.ofNullable(calendar.getDate())
                .ifPresent(date -> findCalendar.setDate(date));
        Optional.ofNullable(calendar.getTime())
                .ifPresent(time -> findCalendar.setTime(time));
        Optional.ofNullable(calendar.getContent())
                .ifPresent(content -> findCalendar.setContent(content));
        Optional.ofNullable(calendar.getParticipant())
                .ifPresent(participant -> findCalendar.setParticipant(participant));

        return calendarRepository.save(findCalendar);
    }

    // 캘린더 삭제
    public void deleteCalendar(long calendarId) {
        Calendar calendar = verifiedCalendar(calendarId);
        calendarRepository.delete(calendar);
    }

    // 캘린더 조회
    public Calendar findCalendar(long calendarId) {
        return verifiedCalendar(calendarId);
    }

    // 캘린더 전체 조회
    public Page<Calendar> findCalendars(int page, int size) {
        return calendarRepository.findAll(PageRequest.of(page, size,
                Sort.by("calendarId").descending()));
    }

    // 캘린더 증명
    public Calendar verifiedCalendar(long calendarId) {
        Optional<Calendar> optionalCalendar = calendarRepository.findById(calendarId);
        Calendar calendar =
                optionalCalendar.orElseThrow(() ->
                        new BusinessLogicException((ExceptionCode.MESSAGE_NOT_FOUND)));

        return calendar;
    }
}
