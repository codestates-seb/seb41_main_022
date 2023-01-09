package codestates.main22.calendar.controller;

import codestates.main22.calendar.dto.CalendarResponseDto;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.mapper.CalendarMapper;
import codestates.main22.calendar.service.CalendarService;
import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.dto.CalendarResponseDto;
import codestates.main22.calendar.entity.Calendar;
import org.springframework.data.domain.Page;
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

    public CalendarController(CalendarService calendarService, CalendarMapper calendarMapper) {
        this.calendarService = calendarService;
        this.calendarMapper = calendarMapper;
    }

    @PostMapping
    public ResponseEntity postCalendar(@Valid @RequestBody CalendarRequestDto.Post post) {
        Calendar calendar = calendarService.createCalendar(calendarMapper.calendarReqPostDtoToCalendar(post));
        CalendarResponseDto.Post response = calendarMapper.calendarToCalendarResPostDto(calendar);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    @GetMapping("/{calendar-id}")
    public ResponseEntity getCalendar(@PathVariable("calendar-id") @Positive long calendarId) {
        Calendar calendar = calendarService.findCalendar(calendarId);
        CalendarResponseDto.Post response = calendarMapper.calendarToCalendarResPostDto(calendar);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getCalendars(@Positive @RequestParam int page,
                                  @Positive @RequestParam int size) {
        Page<Calendar> pageCalendars = calendarService.findCalendars(page - 1, size);
        List<Calendar> calendars = pageCalendars.getContent();
        List<CalendarResponseDto.Post> responses = calendarMapper.calendarsToCalendarResPostDtos(calendars);

        return new ResponseEntity<>(
                new MultiResponseDto<>(responses, pageCalendars),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{calendar-id}")
    public ResponseEntity patchCalendar(@Positive @PathVariable("calendar-id") long calendarId,
                                   @Valid @RequestBody CalendarRequestDto.Patch patch) {
        patch.setCalendarId(calendarId);
        Calendar calendar = calendarService.updateCalendar(calendarMapper.calendarReqPatchDtoToCalendar(patch));
        CalendarResponseDto.Post response = calendarMapper.calendarToCalendarResPostDto(calendar);

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