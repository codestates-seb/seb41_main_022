package codestates.main22.study;

import codestates.main22.dto.SingleResponseDto;
import codestates.main22.study.controller.StudyController;
import codestates.main22.study.dto.*;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.util.JwtMockBean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudyController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
public class StudyControllerDocumentationTest extends JwtMockBean {
    private static UserEntity user1 = new UserEntity();
    private static UserEntity user2 = new UserEntity();
    private static StudyDto.ResponseTag response1;
    private static StudyDto.ResponseTag response2;

    @MockBean
    private StudyService studyService;

    @MockBean
    private StudyMapper studyMapper;

    @BeforeAll
    public static void initAll() {
        user1.setCreatedAt(LocalDateTime.now());
        user1.setModifiedAt(LocalDateTime.now());
        user1.setEmail("hello@gmail.com");
        user1.setInfo("자기소개");
        user1.setUsername("test-name");
        user1.setUserId(1);
        user1.setToken("abc");
        user1.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user1.setRole(new ArrayList<>());
        user1.setUserStudies(new ArrayList<>());

        user2.setCreatedAt(LocalDateTime.now());
        user2.setModifiedAt(LocalDateTime.now());
        user2.setEmail("hello2@gmail.com");
        user2.setInfo("자기소개2");
        user2.setUsername("test-name2");
        user2.setUserId(2);
        user2.setToken("123");
        user2.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user2.setRole(new ArrayList<>());
        user2.setUserStudies(new ArrayList<>());

        response1 = new StudyDto.ResponseTag(
                1L,
                "팀이름",
                "한줄 요약",
                Arrays.asList("IT","수학"),
                Arrays.asList("월","수","금"),
                5,
                LocalDate.now(),
                true,
                true,
                "스터디에 관한 내용입니다.",
                null,
                "https://avatars.dicebear.com/api/bottts/222.svg",
                1L,
                Arrays.asList(2L)
                );

        response2 = new StudyDto.ResponseTag(
                2L,
                "팀이름22",
                "한줄 요약22",
                Arrays.asList("과학","수학"),
                Arrays.asList("월","수","금"),
                5,
                LocalDate.now(),
                true,
                false,
                "스터디에 관한 내용입니다22",
                null,
                "https://avatars.dicebear.com/api/bottts/222.svg",
                2L,
                Arrays.asList(1L)
        );

        startWithUrl = "/study";
    }

    @DisplayName("#40 - 스터디 작성 'Create New Study'")
    @Test
    @WithMockUser
    // TODO #40 - 스터디 작성 'Create New Study' - 통과됨
    public void postStudyTest() throws Exception {
        // given
        StudyDto.Post post = new StudyDto.Post(
                response1.getTeamName(),
                response1.getSummary(),
                response1.getTags(),
                response1.getDayOfWeek(),
                response1.getWant(),
                response1.getStartDate(),
                response1.isProcedure(),
                response1.isOpenClose(),
                response1.getContent(),
                response1.getImage()
        );

        given(studyMapper.studyPostDtoToStudy(Mockito.any(StudyDto.Post.class))).willReturn(new Study());
        given(studyService.createStudy(Mockito.any(Study.class),Mockito.any(HttpServletRequest.class))).willReturn(new Study());
        given(studyService.createTagStudies(Mockito.any(Study.class), Mockito.anyList())).willReturn(new ArrayList<>());
        given(studyMapper.studyToStudyResponseDto(Mockito.any(Study.class),Mockito.anyList())).willReturn(response1);

        // when
        ResultActions actions = mockMvc.perform(
                post(startWithUrl)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("access-Token", "abc")
                        .header("refresh-Token","abc")
                        .content(gson.toJson(post))
                        .characterEncoding("utf-8")
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document(
                        "study/#40",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("tags").type(JsonFieldType.ARRAY).description("태그"),
                                        fieldWithPath("dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("want").type(JsonFieldType.NUMBER).description("모집인원"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작날짜"),
                                        fieldWithPath("procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("image").type(JsonFieldType.STRING).description("대표사진")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.studyId").type(JsonFieldType.NUMBER).description("스터디 식별자"),
                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그"),
                                        fieldWithPath("data.dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("data.want").type(JsonFieldType.NUMBER).description("모집인원"),
                                        fieldWithPath("data.startDate").type(JsonFieldType.STRING).description("시작날짜"),
                                        fieldWithPath("data.procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("data.openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("data.notice").type(JsonFieldType.NULL).description("공지"),
                                        fieldWithPath("data.image").type(JsonFieldType.STRING).description("대표사진"),
                                        fieldWithPath("data.leaderId").type(JsonFieldType.NUMBER).description("스터디장 식별자"),
                                        fieldWithPath("data.requester").type(JsonFieldType.ARRAY).description("가입희망 식별자")
                                )
                        )
                ));
    }

    @DisplayName("#6 - 스터디 전체 조회 (openClose 기준으로) - 처음 조회했을 경우")
    @Test
    @WithMockUser
    // TODO #6 - 스터디 전체 조회 (openClose 기준으로) - 처음 조회했을 경우 - 통과됨
    public void getStudiesByOpenCloseTest() throws Exception {
        // given
        String page = "1";
        String size = "10";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);

        Study study1 = new Study();
        Study study2 = new Study();

        study1.setStudyId(response1.getStudyId());
        study1.setTeamName(response1.getTeamName());
        study1.setSummary(response1.getSummary());
        study1.setDayOfWeek(response1.getDayOfWeek());
        study1.setWant(response1.getWant());
        study1.setStartDate(response1.getStartDate());
        study1.setProcedure(response1.isProcedure());
        study1.setOpenClose(response1.isOpenClose());
        study1.setContent(response1.getContent());
        study1.setNotice(response1.getNotice());
        study1.setImage(response1.getImage());
        study1.setLeaderId(response1.getLeaderId());
        study1.setRequester(response1.getRequester());

        study2.setStudyId(response2.getStudyId());
        study2.setTeamName(response2.getTeamName());
        study2.setSummary(response2.getSummary());
        study2.setDayOfWeek(response2.getDayOfWeek());
        study2.setWant(response2.getWant());
        study2.setStartDate(response2.getStartDate());
        study2.setProcedure(response2.isProcedure());
        study2.setOpenClose(response2.isOpenClose());
        study2.setContent(response2.getContent());
        study2.setNotice(response2.getNotice());
        study2.setImage(response2.getImage());
        study2.setLeaderId(response2.getLeaderId());
        study2.setRequester(response2.getRequester());

        List<Study> studyList = Arrays.asList(study1, study2);
        Page<Study> studies = new PageImpl<>(studyList, PageRequest.of(0, 10), 2);

        StudyDto.CardResponse studyResponse1 = new StudyDto.CardResponse(
                response1.getStudyId(),
                response1.getTeamName(),
                response1.getSummary(),
                response1.getDayOfWeek(),
                response1.isProcedure(),
                response1.getImage()
        );

        StudyDto.CardResponse studyResponse2 = new StudyDto.CardResponse(
                response2.getStudyId(),
                response2.getTeamName(),
                response2.getSummary(),
                response2.getDayOfWeek(),
                response2.isProcedure(),
                response2.getImage()
        );

        List<StudyDto.CardResponse> responses = List.of(studyResponse1, studyResponse2);

        given(studyService.findStudiesByFilters(Mockito.anyInt(), Mockito.anyInt())).willReturn(studies);
        given(studyMapper.studiesToStudyCardResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/first-cards")
                        .params(queryParams)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "study/#6",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 사이즈")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].studyId").type(JsonFieldType.NUMBER).description("스터디 식별자"),
                                        fieldWithPath("data[].teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data[].summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data[].dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("data[].procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("data[].image").type(JsonFieldType.STRING).description("대표사진"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("몇 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("사이즈"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 갯수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지 갯수")
                                )
                        )
                ));
    }

    @DisplayName("#7 - 스터디 전체 조회 (tag 기준 필터링)")
    @Test
    @WithMockUser
    // TODO #7 - 스터디 전체 조회 (tag 기준 필터링) - 통과됨
    public void getStudiesByTagsTest() throws Exception {
        // given
        String page = "1";
        String size = "10";
        String search = "Loud";
        String filter = "random";
        String tags = "IT,수학,과학,토익";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("page", page);
        queryParams.add("size", size);
        queryParams.add("search", search);
        queryParams.add("filter", filter);
        queryParams.add("tags", tags);

        Study study1 = new Study();
        Study study2 = new Study();

        study1.setStudyId(response1.getStudyId());
        study1.setTeamName(response1.getTeamName());
        study1.setSummary(response1.getSummary());
        study1.setDayOfWeek(response1.getDayOfWeek());
        study1.setWant(response1.getWant());
        study1.setStartDate(response1.getStartDate());
        study1.setProcedure(response1.isProcedure());
        study1.setOpenClose(response1.isOpenClose());
        study1.setContent(response1.getContent());
        study1.setNotice(response1.getNotice());
        study1.setImage(response1.getImage());
        study1.setLeaderId(response1.getLeaderId());
        study1.setRequester(response1.getRequester());

        study2.setStudyId(response2.getStudyId());
        study2.setTeamName(response2.getTeamName());
        study2.setSummary(response2.getSummary());
        study2.setDayOfWeek(response2.getDayOfWeek());
        study2.setWant(response2.getWant());
        study2.setStartDate(response2.getStartDate());
        study2.setProcedure(response2.isProcedure());
        study2.setOpenClose(response2.isOpenClose());
        study2.setContent(response2.getContent());
        study2.setNotice(response2.getNotice());
        study2.setImage(response2.getImage());
        study2.setLeaderId(response2.getLeaderId());
        study2.setRequester(response2.getRequester());

        List<Study> studyList = Arrays.asList(study1, study2);
        Page<Study> studies = new PageImpl<>(studyList, PageRequest.of(0, 10), 2);

        StudyDto.CardResponse studyResponse1 = new StudyDto.CardResponse(
                response1.getStudyId(),
                response1.getTeamName(),
                response1.getSummary(),
                response1.getDayOfWeek(),
                response1.isProcedure(),
                response1.getImage()
        );

        StudyDto.CardResponse studyResponse2 = new StudyDto.CardResponse(
                response2.getStudyId(),
                response2.getTeamName(),
                response2.getSummary(),
                response2.getDayOfWeek(),
                response2.isProcedure(),
                response2.getImage()
        );

        List<StudyDto.CardResponse> responses = List.of(studyResponse1, studyResponse2);

        given(studyService.findStudiesByFilters(Mockito.anyInt(), Mockito.anyInt())).willReturn(studies);
        given(studyMapper.studiesToStudyCardResponseDto(Mockito.anyList())).willReturn(responses);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/first-cards")
                        .params(queryParams)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "study/#7",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                List.of(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 사이즈"),
                                        parameterWithName("search").description("검색어"),
                                        parameterWithName("filter").description("필터"),
                                        parameterWithName("tags").description("태그")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].studyId").type(JsonFieldType.NUMBER).description("스터디 식별자"),
                                        fieldWithPath("data[].teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data[].summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data[].dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("data[].procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("data[].image").type(JsonFieldType.STRING).description("대표사진"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("몇 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("사이즈"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 갯수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("페이지 갯수")
                                )
                        )
                ));
    }

    @DisplayName("#23 - 스터디 삭제 (방장 권한으로)")
    @Test
    @WithMockUser
    // TODO #23 - 스터디 삭제 (방장 권한으로) - 통과됨
    public void deleteStudyTest() throws Exception {
        // given
        long studyId = 1L;

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(token.findByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());

        // when
        ResultActions actions = mockMvc.perform(
                delete(startWithUrl + "/{study-id}", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("access-Token", "abc")
                        .header("refresh-Token","abc")
        );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "study/#23",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        )
                ));
    }

    @DisplayName("#24 - 스터디 탈퇴 (멤버인 경우에만)")
    @Test
    @WithMockUser
    // TODO #24 - 스터디 탈퇴 (멤버인 경우에만) - 통과됨
    public void withdrawStudyTest() throws Exception {
        // given
        long studyId = 1L;
        long userId = 2L;

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyService.isMember(Mockito.anyLong(), Mockito.anyLong())).willReturn(true);

        // when
        ResultActions actions = mockMvc.perform(
                delete(startWithUrl + "/{study-id}/{user-id}", studyId, userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document(
                        "study/#24",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자"),
                                parameterWithName("user-id").description("유저 식별자")
                        )
                ));
    }

    @DisplayName("#17 - studyHall/Notification 에서 공지만 확인")
    @Test
    @WithMockUser
    // TODO #17 - studyHall/Notification 에서 공지만 확인 - 통과됨
    public void getNotificationTest() throws Exception {
        // given
        long studyId = 1L;
        StudyNotificationDto.Response response = new StudyNotificationDto.Response("공지");

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyNotificationResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}/notification", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#17",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.notice").type(JsonFieldType.STRING).description("공지")
                                )
                        )
                ));
    }

    @DisplayName("#18 - studyHall/Notification 에서 공지만 수정")
    @Test
    @WithMockUser
    // TODO #18 - studyHall/Notification 에서 공지만 수정 - 통과됨
    public void patchNotificationTest() throws Exception {
        // given
        long studyId = 1L;

        StudyNotificationDto.Patch patch = new StudyNotificationDto.Patch(
                Arrays.asList("FRI"),
                "공지22"
        );

        StudyNotificationDto.Response response = new StudyNotificationDto.Response("공지22");

        given(studyMapper.studyNotificationPatchDtoToStudyNotification(Mockito.any(StudyNotificationDto.Patch.class))).willReturn(new Study());
        given(studyService.updateStudyNotice(Mockito.anyLong(),Mockito.any(Study.class))).willReturn(new Study());
        given(studyMapper.studyToStudyNotificationResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                patch(startWithUrl + "/{study-id}/notification", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(patch))
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#18",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("notice").type(JsonFieldType.STRING).description("공지"),
                                        fieldWithPath("dayOfWeek").type(JsonFieldType.ARRAY).description("요일")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.notice").type(JsonFieldType.STRING).description("공지")
                                )
                        )
                ));
    }

    @DisplayName("#30 - studyHall/main 에서 공지사항 확인")
    @Test
    @WithMockUser
    // TODO #30 - studyHall/main 에서 공지사항 확인 - 통과됨
    public void getNoticeTest() throws Exception {
        // given
        long studyId = 1L;
        StudyNotificationDto.NoticeResponse response =
                new StudyNotificationDto.NoticeResponse(
                        Arrays.asList("MON", "FRI"),
                        "나 이제 6학년이니까 내말 잘 들어라 -도봉초 불은매 김현우");

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyNoticeResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}/notice", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#30",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("data.notice").type(JsonFieldType.STRING).description("공지")
                                )
                        )
                ));
    }

    @DisplayName("#39 - main 스터디 신청 : 버튼이 이미 활성화 되어 있다 가정")
    @Test
    @WithMockUser
    // TODO #39 - main 스터디 신청 : 버튼이 이미 활성화 되어 있다 가정 - 통과됨
    public void registerStudyTest() throws Exception {
        // given
        long studyId = 1L;

        StudyRequesterDto.Response response = new StudyRequesterDto.Response(new ArrayList<>());
        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(token.findByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(studyMapper.studyToStudyRequesterResponseDto(Mockito.any(Study.class)))
                .willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                post(startWithUrl + "/{study-id}/requester", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("access-Token", "123")
                        .header("refresh-Token","123")
                        .characterEncoding("utf-8")
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document(
                        "study/#39",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        )
                ));
    }

    @DisplayName("#26 - main 스터디 신청 수락")
    @Test
    @WithMockUser
    // TODO #26 - main 스터디 신청 수락 - 통과됨
    public void acceptRegisterStudyTest() throws Exception {
        // given
        long studyId = 1L;
        long userId = 2L;

        StudyRequesterDto.Response response = new StudyRequesterDto.Response(new ArrayList<>());
        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyService.giveUserAuth(Mockito.any(Study.class),Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyRequesterResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                post(startWithUrl + "/{study-id}/requester/{user-id}/accept", studyId, userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document(
                        "study/#26",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자"),
                                parameterWithName("user-id").description("유저 식별자")
                        )
                ));
    }

    @DisplayName("#27 - main 스터디 신청 거절")
    @Test
    @WithMockUser
    // TODO #27 - main 스터디 신청 거절 - 통과됨
    public void rejectRegisterStudyTest() throws Exception {
        // given
        long studyId = 1L;
        long userId = 2L;

        StudyRequesterDto.Response response = new StudyRequesterDto.Response(new ArrayList<>());
        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyRequesterResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                post(startWithUrl + "/{study-id}/requester/{user-id}/reject", studyId, userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document(
                        "study/#27",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자"),
                                parameterWithName("user-id").description("유저 식별자")
                        )
                ));
    }

    @DisplayName("#28 - 각종 true false 변수들 넘겨주기 (token 값을 사용)")
    @Test
    @WithMockUser
    // TODO #28 - 각종 true false 변수들 넘겨주기 (token 값을 사용) - 통과됨
    public void getAuthTest() throws Exception {
        // 현재 테스트 내용 : 1번 스터디에 2번 유저가 가입 신청만 한 상태이다. 멤버는 아님.
        // given
        long studyId = 1L;

        Study study = new Study();
        study.setLeaderId(1L);
        study.setRequester(List.of(2L));

        UserEntity user = new UserEntity();
        user.setUserId(2L);

        given(studyService.findStudy(Mockito.anyLong())).willReturn(study);
        given(token.findByToken(Mockito.any(HttpServletRequest.class))).willReturn(user);
        given(studyService.isMember(Mockito.anyLong(),Mockito.anyLong())).willReturn(false);
        given(studyMapper.studyToStudyAuthResponseDto(
                Mockito.any(Study.class),
                Mockito.anyBoolean(),
                Mockito.anyBoolean(),
                Mockito.anyBoolean())
        ).willReturn(new StudyMainDto.AuthResponse(false, false, true));

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}/auth", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("access-Token", "123")
                        .header("refresh-Token","123")
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "study/#28/token",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.member").type(JsonFieldType.BOOLEAN).description("멤버 여부"),
                                        fieldWithPath("data.host").type(JsonFieldType.BOOLEAN).description("방장 여부"),
                                        fieldWithPath("data.request").type(JsonFieldType.BOOLEAN).description("신청 여부")
                                )
                        )
                ));
    }

    @DisplayName("#28 - 각종 true false 변수들 넘겨주기 (token 값 사용 X)")
    @Test
    @WithMockUser
    // TODO #28 - 각종 true false 변수들 넘겨주기 (token 값 사용 X) - 통과됨
    public void getAuthWithUserIdTest() throws Exception {
        // 현재 테스트 내용 : 1번 스터디에 2번 유저가 가입 신청만 한 상태이다. 멤버는 아님.
        // given
        long studyId = 1L;
        long userId = 2L;

        Study study = new Study();
        study.setLeaderId(1L);
        study.setRequester(List.of(2L));

        UserEntity user = new UserEntity();
        user.setUserId(2L);

        given(studyService.findStudy(Mockito.anyLong())).willReturn(study);
        given(studyService.isMember(Mockito.anyLong(),Mockito.anyLong())).willReturn(false);
        given(studyMapper.studyToStudyAuthResponseDto(
                Mockito.any(Study.class),
                Mockito.anyBoolean(),
                Mockito.anyBoolean(),
                Mockito.anyBoolean())
        ).willReturn(new StudyMainDto.AuthResponse(false, false, true));

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}/user/{user-id}/auth", studyId, userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("access-Token", "123")
                        .header("refresh-Token","123")
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "study/#28/noToken",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자"),
                                parameterWithName("user-id").description("유저 식별자")
                        ),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.member").type(JsonFieldType.BOOLEAN).description("멤버 여부"),
                                        fieldWithPath("data.host").type(JsonFieldType.BOOLEAN).description("방장 여부"),
                                        fieldWithPath("data.request").type(JsonFieldType.BOOLEAN).description("신청 여부")
                                )
                        )
                ));
    }

    @DisplayName("#29 - studyHall/main 윗부분 header")
    @Test
    @WithMockUser
    // TODO #29 - studyHall/main 윗부분 header - 통과됨
    public void getMainHeaderTest() throws Exception {
        // given
        long studyId = 1L;
        StudyMainDto.HeaderResponse response = new StudyMainDto.HeaderResponse("Your Study",false, true);

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyHeaderResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}/header", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#29",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("data.openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개")
                                )
                        )
                ));
    }

    @DisplayName("#31 - studyHall/main 본문")
    @Test
    @WithMockUser
    // TODO #31 - studyHall/main 본문 - 통과됨
    public void getMainBodyTest() throws Exception {
        // given
        long studyId = 1L;
        StudyMainDto.MainResponse response =
                new StudyMainDto.MainResponse(
                        "Your Study",
                        "집주변 카페에 모여 토론하는 스터디입니다! 가끔 인스타 핫카페에서 친목도 해요",
                        Arrays.asList("IT", "수학"),
                        "Our study is an 80-year old coding study...");

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyMainResponseDto(Mockito.any(Study.class),Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}/main", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#31",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문")
                                )
                        )
                ));
    }

    @DisplayName("#33 - studyHall/main 본문 수정")
    @Test
    @WithMockUser
    // TODO #33 - studyHall/main 본문 수정 - 통과됨
    public void patchMainBodyTest() throws Exception {
        // given
        long studyId = 1L;

        StudyMainDto.MainPatch patch = new StudyMainDto.MainPatch(
                "Your Study",
                "집주변 카페에 모여 토론하는 스터디입니다",
                Arrays.asList("IT","수학","일본어"),
                Arrays.asList("TUE","WED","FRI"),
                3,
                LocalDate.now(),
                false,
                false,
                "Our study is an 80-year old coding study...",
                "https://seb41-main-022.s3.ap-northeast-2.amazonaws.com/main22.png"
        );

        StudyMainDto.MainPatch response = new StudyMainDto.MainPatch(
                "Your Study",
                "집주변 카페에 모여 토론하는 스터디입니다",
                Arrays.asList("IT","수학","일본어"),
                Arrays.asList("TUE","WED","FRI"),
                3,
                LocalDate.now(),
                false,
                false,
                "Our study is an 80-year old coding study...",
                "https://seb41-main-022.s3.ap-northeast-2.amazonaws.com/main22.png"
        );

        given(studyMapper.studyMainPatchDtoToStudyMain(Mockito.any(StudyMainDto.MainPatch.class))).willReturn(new Study());
        given(studyService.updateMainBody(Mockito.anyLong(),Mockito.any(Study.class))).willReturn(new Study());
        given(studyService.updateTag(Mockito.any(Study.class),Mockito.anyList())).willReturn(new ArrayList<>());
        given(studyMapper.studyToStudyMainPatchResponseDto(Mockito.any(Study.class),Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                patch(startWithUrl + "/{study-id}/main", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(patch))
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#33",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("tags").type(JsonFieldType.ARRAY).description("태그"),
                                        fieldWithPath("dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("want").type(JsonFieldType.NUMBER).description("모집인원"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("시작날짜"),
                                        fieldWithPath("procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("image").type(JsonFieldType.STRING).description("대표사진")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그"),
                                        fieldWithPath("data.dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("data.want").type(JsonFieldType.NUMBER).description("모집인원"),
                                        fieldWithPath("data.startDate").type(JsonFieldType.STRING).description("시작날짜"),
                                        fieldWithPath("data.procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("data.openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("data.image").type(JsonFieldType.STRING).description("대표사진")
                                )
                        )
                ));
    }

    @DisplayName("#9 - user의 study 조회")
    @Test
    @WithMockUser
    // TODO #9 - user의 study 조회 - 통과됨
    public void getStudiesByUserTest() throws Exception {
        // given
        // long studyId = 1L;
        List<StudyUserDto.Studys> response = new ArrayList<>();
        response.add(new StudyUserDto.Studys(
                response1.getStudyId(),
                response1.getTeamName(),
                response1.getSummary(),
                response1.getImage()
        ));
        response.add(new StudyUserDto.Studys(
                response2.getStudyId(),
                response2.getTeamName(),
                response2.getSummary(),
                response2.getImage()
        ));

        given(studyService.findStudiesByUser(Mockito.any(HttpServletRequest.class))).willReturn(new ArrayList<>());
        given(studyMapper.studiesToStudyUserDto(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/user")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("access-Token", "abc")
                        .header("refresh-Token","abc")
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "study/#9",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.studyCount").type(JsonFieldType.NUMBER).description("스터디 갯수"),
                                        fieldWithPath("data.studies").type(JsonFieldType.ARRAY).description("스터디 리스트"),
                                        fieldWithPath("data.studies[].studyId").type(JsonFieldType.NUMBER).description("스터디 식별자"),
                                        fieldWithPath("data.studies[].teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.studies[].summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data.studies[].image").type(JsonFieldType.STRING).description("대표사진")
                                )
                        )
                ));
    }

    @DisplayName("#44 - 스터디 내용 조회")
    @Test
    @WithMockUser
    // TODO #44 - 스터디 내용 조회 (추가된 테스트) (기본 CRUD) - 통과됨
    public void getStudyTest() throws Exception {
        // given
        long studyId = 1L;
        StudyDto.Response response =
                new StudyDto.Response(
                        1L,
                        response1.getTeamName(),
                        response1.getSummary(),
                        response1.getDayOfWeek(),
                        response1.getWant(),
                        response1.getStartDate(),
                        response1.isProcedure(),
                        response1.isOpenClose(),
                        response1.getContent(),
                        "공지입니다",
                        response1.getImage(),
                        1L,
                        new ArrayList<>());

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(studyMapper.studyToStudyResponseDto(Mockito.any(Study.class))).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/{study-id}", studyId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
                .andDo(document(
                        "study/#44",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.studyId").type(JsonFieldType.NUMBER).description("스터디 식별자"),
                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data.dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
                                        fieldWithPath("data.want").type(JsonFieldType.NUMBER).description("모집인원"),
                                        fieldWithPath("data.startDate").type(JsonFieldType.STRING).description("시작날짜"),
                                        fieldWithPath("data.procedure").type(JsonFieldType.BOOLEAN).description("온라인/오프라인"),
                                        fieldWithPath("data.openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                                        fieldWithPath("data.notice").type(JsonFieldType.STRING).description("공지"),
                                        fieldWithPath("data.image").type(JsonFieldType.STRING).description("대표사진"),
                                        fieldWithPath("data.leaderId").type(JsonFieldType.NUMBER).description("스터디장 식별자"),
                                        fieldWithPath("data.requester").type(JsonFieldType.ARRAY).description("가입희망 식별자")
                                )
                        )
                ));
    }

    @DisplayName("#48 - 토큰 없이 스터디 검색하기")
    @Test
    @WithMockUser
    // TODO #48 - 토큰 없이 스터디 검색하기 - 통과됨
    public void getStudiesByUserNoTokenTest() throws Exception {
        // given
        // long studyId = 1L;
        long userId = 1L;
        List<StudyUserDto.Studys> response = new ArrayList<>();
        response.add(new StudyUserDto.Studys(
                response1.getStudyId(),
                response1.getTeamName(),
                response1.getSummary(),
                response1.getImage()
        ));
        response.add(new StudyUserDto.Studys(
                response2.getStudyId(),
                response2.getTeamName(),
                response2.getSummary(),
                response2.getImage()
        ));

        given(studyService.findStudiesByUserNoToken(Mockito.anyLong())).willReturn(new ArrayList<>());
        given(studyMapper.studiesToStudyUserDto(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(
                get(startWithUrl + "/user/{user-id}", userId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"));

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "study/#48",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("user-id").description("유저 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.studyCount").type(JsonFieldType.NUMBER).description("스터디 갯수"),
                                        fieldWithPath("data.studies").type(JsonFieldType.ARRAY).description("스터디 리스트"),
                                        fieldWithPath("data.studies[].studyId").type(JsonFieldType.NUMBER).description("스터디 식별자"),
                                        fieldWithPath("data.studies[].teamName").type(JsonFieldType.STRING).description("팀이름"),
                                        fieldWithPath("data.studies[].summary").type(JsonFieldType.STRING).description("한줄설명"),
                                        fieldWithPath("data.studies[].image").type(JsonFieldType.STRING).description("대표사진")
                                )
                        )
                ));
    }
}
