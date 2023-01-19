package codestates.main22.calendar.controller;

import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.mapper.CalendarMapper;
import codestates.main22.calendar.service.CalendarService;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.dto.CalendarResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@Valid
public class CalendarController {
    private final CalendarService calendarService;
    private final CalendarMapper calendarMapper;

    public CalendarController(CalendarService calendarService,
                              CalendarMapper calendarMapper) {
        this.calendarService = calendarService;
        this.calendarMapper = calendarMapper;
    }

    @PostMapping
    public ResponseEntity postCalendar(@Valid @RequestBody CalendarRequestDto.Post post,
                                       @RequestParam @Positive long studyId) {

        Calendar calendar = calendarService.createCalendar(calendarMapper.calendarReqPostDtoToCalendar(post), studyId);
//        List<ParticipantResponseDto.Post> participants = participantMapper.participantsToParticipantResPostDtos(calendar.getParticipants());
        CalendarResponseDto response = calendarMapper.calendarToCalendarResDto(calendar);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity getCalendars(@RequestParam @Positive long studyId) {
        List<Calendar> calendars = calendarService.findCalendarByStudyId(studyId);
        List<CalendarResponseDto> response = calendarMapper.calendarsToCalendarResDtos(calendars);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

//    @GetMapping
//    public ResponseEntity getCalendars(@Positive @RequestParam int page,
//                                  @Positive @RequestParam int size) {
//        Page<Calendar> pageCalendars = calendarService.findCalendars(page - 1, size);
//        List<Calendar> calendars = pageCalendars.getContent();
//        List<CalendarResponseDto> responses = calendarMapper.calendarsToCalendarResDtos(calendars);
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(responses, pageCalendars),
//                HttpStatus.OK
//        );
//    }

    @PatchMapping("/{calendar-id}")
    public ResponseEntity patchCalendar(@Positive @PathVariable("calendar-id") long calendarId,
                                   @Valid @RequestBody CalendarRequestDto.Patch patch) {
        patch.setCalendarId(calendarId);
        Calendar calendar = calendarService.updateCalendar(calendarMapper.calendarReqPatchDtoToCalendar(patch));
        CalendarResponseDto response = calendarMapper.calendarToCalendarResDto(calendar);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @DeleteMapping("/{calendar-id}")
    public ResponseEntity deleteCalendar(@Positive @PathVariable("calendar-id") long calendarId) {
        calendarService.deleteCalendar(calendarId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}