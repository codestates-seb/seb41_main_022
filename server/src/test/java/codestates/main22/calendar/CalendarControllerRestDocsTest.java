package codestates.main22.calendar;

import codestates.main22.calendar.controller.CalendarController;
import codestates.main22.calendar.dto.CalendarRequestDto;
import codestates.main22.calendar.dto.CalendarResponseDto;
import codestates.main22.calendar.entity.Calendar;
import codestates.main22.calendar.mapper.CalendarMapper;
import codestates.main22.calendar.service.CalendarService;
import codestates.main22.participant.dto.ParticipantRequestDto;
import codestates.main22.participant.dto.ParticipantResponseDto;
import codestates.main22.participant.entity.Participant;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.util.JwtMockBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.nimbusds.jose.shaded.json.parser.JSONParser;
import org.hibernate.query.QueryParameter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalendarController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
public class CalendarControllerRestDocsTest extends JwtMockBean {
    private static List<ParticipantResponseDto> participants;
    private static CalendarResponseDto response;

    @MockBean
    private CalendarService calendarService;

    @MockBean
    private CalendarMapper calendarMapper;

    @BeforeAll
    public static void initAll() {
        participants = new ArrayList<>();
        participants.add(new ParticipantResponseDto(
                1L,
                "사용자1",
                Participant.Attendance.NONE
        ));
        participants.add(new ParticipantResponseDto(
                2L,
                "사용자2",
                Participant.Attendance.NONE
        ));

        response = new CalendarResponseDto(
                1L,
                "스터디 모임!!",
                LocalDateTime.now().withNano(0),
                participants
        );

        startWithUrl = "/calendar";
    }

    @DisplayName("#19 - studyHall/Notification 캘린더 생성")
    @WithMockUser
    @Test
    public void postCalendarTest() throws Exception {
        // given
        CalendarRequestDto.Post post = new CalendarRequestDto.Post(
                response.getTitle(), response.getDate()
        );

        given(calendarMapper.calendarReqPostDtoToCalendar(Mockito.any(CalendarRequestDto.Post.class))).willReturn(new Calendar());

        given(calendarService.createCalendar(Mockito.any(Calendar.class), Mockito.anyLong())).willReturn(new Calendar());

        given(calendarMapper.calendarToCalendarResDto(Mockito.any(Calendar.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post(startWithUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(post))
                                .characterEncoding("utf-8")
                                .param("studyId", "1")
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
                .andDo(document("calendar/#19",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("스터디 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("date").type(JsonFieldType.STRING).description("날짜")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.calendarId").type(JsonFieldType.NUMBER).description("캘린더 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("data.date").type(JsonFieldType.STRING).description("날짜"),
                                        fieldWithPath("data.participants").type(JsonFieldType.ARRAY).description("참가자 리스트"),
                                        fieldWithPath("data.participants[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("data.participants[].username").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.participants[].joinState").type(JsonFieldType.STRING).description("참가여부")
                                )
                        )
                ));
    }

    @DisplayName("#20 - studyHall/Notification 캘린더 조회")
    @WithMockUser
    @Test
    public void getCalendarsTest() throws Exception {
        // given
        List<CalendarResponseDto> response = new ArrayList<>();
        response.add(this.response);
        response.add(this.response);

        given(calendarService.findCalendarByStudyId(Mockito.anyLong())).willReturn(new ArrayList<Calendar>());

        given(calendarMapper.calendarsToCalendarResDtos(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get(startWithUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("studyId", "1")
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value(response.get(0).getTitle()))
                .andDo(document("calendar/#20",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].calendarId").type(JsonFieldType.NUMBER).description("캘린더 식별자"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("data[].date").type(JsonFieldType.STRING).description("날짜"),
                                        fieldWithPath("data[].participants").type(JsonFieldType.ARRAY).description("참가자 리스트"),
                                        fieldWithPath("data[].participants[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("data[].participants[].username").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].participants[].joinState").type(JsonFieldType.STRING).description("참가여부")
                                )
                        )
                ));
    }

    @DisplayName("#21 - studyHall/Notification 캘린더 수정")
    @WithMockUser
    @Test
    public void patchCalendarTest() throws Exception {
        // given
        // request - patch
        long calendarId = 1L;
        List<ParticipantRequestDto> participants = new ArrayList<>();
        this.participants.forEach(participantResponseDto -> {
            participants.add(new ParticipantRequestDto(
                    participantResponseDto.getUserId(),
                    participantResponseDto.getUsername(),
                    Participant.Attendance.YES
            ));
        });

        CalendarRequestDto.Patch patch = new CalendarRequestDto.Patch(
                calendarId,
                response.getTitle(),
                response.getDate(),
                participants
        );

        // response
        List<ParticipantResponseDto> participantResponseDtos = new ArrayList<>();
        this.participants.forEach(participantResponseDto -> {
            participantResponseDtos.add(new ParticipantResponseDto(
                    participantResponseDto.getUserId(),
                    participantResponseDto.getUsername(),
                    Participant.Attendance.YES
            ));
        });

        CalendarResponseDto response = new CalendarResponseDto(
                calendarId,
                this.response.getTitle(),
                this.response.getDate(),
                participantResponseDtos
        );

        given(calendarMapper.calendarReqPatchDtoToCalendar(Mockito.any(CalendarRequestDto.Patch.class))).willReturn(new Calendar());

        given(calendarService.updateCalendar(Mockito.any(Calendar.class))).willReturn(new Calendar());

        given(calendarMapper.calendarToCalendarResDto(Mockito.any(Calendar.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch(startWithUrl + "/{calendar-id}", calendarId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(patch))
                                .characterEncoding("utf-8")
                );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("calendar/#21",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("calendar-id").description("캘린더 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("calendarId").type(JsonFieldType.NUMBER).description("캘린더 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("date").type(JsonFieldType.STRING).description("날짜"),
                                        fieldWithPath("participants").type(JsonFieldType.ARRAY).description("참가자 리스트"),
                                        fieldWithPath("participants[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("participants[].username").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("participants[].joinState").type(JsonFieldType.STRING).description("참가여부")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.calendarId").type(JsonFieldType.NUMBER).description("캘린더 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("data.date").type(JsonFieldType.STRING).description("날짜"),
                                        fieldWithPath("data.participants").type(JsonFieldType.ARRAY).description("참가자 리스트"),
                                        fieldWithPath("data.participants[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("data.participants[].username").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.participants[].joinState").type(JsonFieldType.STRING).description("참가여부")
                                )
                        )
                ));
    }

    @DisplayName("#22 - studyHall/Notification 캘린더 삭제")
    @WithMockUser
    @Test
    public void deleteCalendarTest() throws Exception {
        // given
        long calendarId = 1L;

        // when
        ResultActions actions =
                mockMvc.perform(
                        delete(startWithUrl + "/{calendar-id}", calendarId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("calendar/#22",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("calendar-id").description("캘린더 식별자")
                        )
                ));
    }

}
