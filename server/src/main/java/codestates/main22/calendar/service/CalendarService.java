package codestates.main22.calendar.service;

import codestates.main22.participant.entity.Participant;
import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.repository.CalendarRepository;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.participant.service.ParticipantService;
import codestates.main22.study.entity.Study;
import codestates.main22.study.service.StudyService;
import codestates.main22.tree.service.TreeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final ParticipantService participantService;
    private final StudyService studyService;
    private final TreeService treeService;

    public CalendarService(CalendarRepository calendarRepository,
                           ParticipantService participantService,
                           StudyService studyService,
                           TreeService treeService) {
        this.calendarRepository = calendarRepository;
        this.participantService = participantService;
        this.studyService = studyService;
        this.treeService = treeService;
    }

    // 캘린더 생성
    public Calendar createCalendar(Calendar calendar, long studyId) {
        // study와 연관관계 연결
        Study study = studyService.findStudy(studyId);
        study.getCalendars().add(calendar);
        calendar.setStudy(study);

        // participant와 연관관계 연결
        List<Participant> participants = participantService.createParticipants(calendar, studyId);
        calendar.setParticipants(participants);

        return calendarRepository.save(calendar);
    }

    // 참가자의 참가 여부에 따른 트리 포인트 업데이트
    public void updateTreePoint(Calendar beforeCalendar, Calendar afterCalendar) {
        int beforePoint = beforeCalendar.getParticipants().stream()
                .mapToInt(participant -> {
                    if(participant.getJoinState() == Participant.Attendance.YES) return 10;
                    else if(participant.getJoinState() == Participant.Attendance.NO) return -10;
                    return 0;
                }).sum();
        int afterPoint = afterCalendar.getParticipants().stream()
                .mapToInt(participant -> {
                    if(participant.getJoinState() == Participant.Attendance.YES) return 10;
                    else if(participant.getJoinState() == Participant.Attendance.NO) return -10;
                    return 0;
                }).sum();

        treeService.updateTreePoint(beforeCalendar.getStudy(), afterPoint-beforePoint);
    }


    // 캘린더 수정
    public Calendar updateCalendar(Calendar calendar) {
        Calendar findCalendar = verifiedCalendar(calendar.getCalendarId());

        updateTreePoint(findCalendar, calendar); // 참가자의 참가 여부에 따른 트리 포인트 업데이트
        List<Participant> participants = participantService.updateParticipants(findCalendar, calendar); // 참여 여부 업데이트

        Optional.ofNullable(calendar.getDate())
                .ifPresent(date -> findCalendar.setDate(date));
        Optional.ofNullable(calendar.getTitle())
                .ifPresent(title -> findCalendar.setTitle(title));
        Optional.ofNullable(calendar.getParticipants())
                .ifPresent(participant -> findCalendar.setParticipants(participants));

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

    // 캘린더 조회 by studyId
    public List<Calendar> findCalendarByStudyId(long studyId) {
        Study study = studyService.findStudy(studyId);
        return calendarRepository.findByStudy(study);
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
