package codestates.main22.calendar.mapper;

import codestates.main22.calendar.dto.CalendarReponseDto;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.entity.Calendar;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CalendarMapper {
    Calendar calendarPostDtoToCalender(CalendarRequestDto.Post post);
    CalendarReponseDto.Post calendarToCalenderPostDto(Calendar calendar);
}