package codestates.main22.calendar.mapper;

import codestates.main22.calendar.dto.CalendarResponseDto;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.entity.Calendar;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    Calendar calendarReqPostDtoToCalendar(CalendarRequestDto.Post post);
    Calendar calendarReqPatchDtoToCalendar(CalendarRequestDto.Patch patch);
    CalendarResponseDto.Post calendarToCalendarResPostDto(Calendar calendar);
    List<CalendarResponseDto.Post> calendarsToCalendarResPostDtos(List<Calendar> calendars);
}