package codestates.main22.calendar.controller;

import codestates.main22.calendar.dto.CalendarReponseDto;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.mapper.CalendarMapper;
import codestates.main22.calendar.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity postCalender(@Valid @RequestBody CalendarRequestDto.Post post) {
        Calendar calendar = calendarService.createCalendar(calendarMapper.calendarPostDtoToCalender(post));
        CalendarReponseDto.Post response = calendarMapper.calendarToCalenderPostDto(calendar);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}