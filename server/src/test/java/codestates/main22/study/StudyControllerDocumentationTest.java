//package codestates.main22.study;
//
//import codestates.main22.dto.SingleResponseDto;
//import codestates.main22.oauth2.jwt.JwtTokenizer;
//import codestates.main22.oauth2.utils.CustomAuthorityUtils;
//import codestates.main22.study.controller.StudyController;
//import codestates.main22.study.dto.StudyDto;
//import codestates.main22.study.dto.StudyMainDto;
//import codestates.main22.study.dto.StudyNotificationDto;
//import codestates.main22.study.dto.StudyUserDto;
//import codestates.main22.study.entity.Study;
//import codestates.main22.study.mapper.StudyMapper;
//import codestates.main22.study.service.StudyService;
//import codestates.main22.user.mapper.UserMapper;
//import codestates.main22.user.service.UserService;
//import codestates.main22.utils.Token;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//
//import java.util.Arrays;
//import java.util.List;
//
//import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
//import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(StudyController.class)
//@MockBean(JpaMetamodelMappingContext.class)
////@MockBean
//@AutoConfigureRestDocs
//public class StudyControllerDocumentationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private UserMapper userMapper;
//
//    @MockBean
//    private StudyService studyService;
//
//    @MockBean
//    private StudyMapper studyMapper;
//
//    @MockBean
//    private JwtTokenizer jwtTokenizer;
//
//    @MockBean
//    private CustomAuthorityUtils customAuthorityUtils;
//
//    @MockBean
//    private Token token;
//
//    @Autowired
//    private Gson gson;
//
//    @BeforeAll
//    public static void initAll() {
//
//    }
//
//    @DisplayName("#40 - 스터디 작성 'Create New Study'")
//    @Test
//    @WithMockUser
//    // TODO #40 - 스터디 작성 'Create New Study'
//    public void postStudyTest() throws Exception {
//
//    }
//
//    @DisplayName("#6 - 스터디 전체 조회 (openClose 기준으로) - 처음 조회했을 경우")
//    @Test
//    @WithMockUser
//    // TODO #6 - 스터디 전체 조회 (openClose 기준으로) - 처음 조회했을 경우
//    public void getStudiesByOpenCloseTest() throws Exception {
//
//    }
//
//    @DisplayName("#7 - 스터디 전체 조회 (tag 기준 필터링)")
//    @Test
//    @WithMockUser
//    // TODO #7 - 스터디 전체 조회 (tag 기준 필터링)
//    public void getStudiesByTagsTest() throws Exception {
//
//    }
//
//    @DisplayName("#23 - 스터디 삭제 (방장 권한으로)")
//    @Test
//    @WithMockUser
//    // TODO #23 - 스터디 삭제 (방장 권한으로)
//    //  시도는 해봤는데 204에러가 뜸
//    public void deleteStudyTest() throws Exception {
//        // given
//        long studyId = 1L;
//
//        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                delete("/study/{study-id}", studyId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isNoContent())
//                .andDo(document(
//                        "study/#23",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        pathParameters(
//                                parameterWithName("study-id").description("스터디 식별자")
//                        )
//                ));
//    }
//
//    @DisplayName("#24 - 스터디 탈퇴 (멤버인 경우에만)")
//    @Test
//    @WithMockUser
//    // TODO #24 - 스터디 탈퇴 (멤버인 경우에만)
//    //  시도는 해봤는데 204에러가 뜸
//    public void withdrawStudyTest() throws Exception {
//        // given
//        long studyId = 1L;
//        long userId = 2L;
//
//        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                delete("/study/{study-id}/{user-id}", studyId, userId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isNoContent())
//                .andDo(document(
//                        "study/#24",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        pathParameters(
//                                parameterWithName("study-id").description("스터디 식별자")
//                        )
//                ));
//    }
//
//    @DisplayName("#17 - studyHall/Notification 에서 공지만 확인")
//    @Test
//    @WithMockUser
//    // TODO #17 - studyHall/Notification 에서 공지만 확인
//    public void getNotificationTest() throws Exception {
//        // given
//        long studyId = 1L;
//        StudyNotificationDto.Response response = new StudyNotificationDto.Response("공지");
//
//        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
//        given(studyMapper.studyToStudyNotificationResponseDto(Mockito.any(Study.class))).willReturn(response);
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                get("/study/{study-id}/notification", studyId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
//                .andDo(document(
//                        "study/#17",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.notice").type(JsonFieldType.STRING).description("공지")
//                                )
//                        )
//                ));
//    }
//
//    @DisplayName("#18 - studyHall/Notification 에서 공지만 수정")
//    @Test
//    @WithMockUser
//    // TODO #18 - studyHall/Notification 에서 공지만 수정
//    public void patchNotificationTest() throws Exception {
//
//    }
//
//    @DisplayName("#30 - studyHall/main 에서 공지사항 확인")
//    @Test
//    @WithMockUser
//    // TODO #30 - studyHall/main 에서 공지사항 확인
//    public void getNoticeTest() throws Exception {
//        // given
//        long studyId = 1L;
//        StudyNotificationDto.NoticeResponse response =
//                new StudyNotificationDto.NoticeResponse(
//                        Arrays.asList("MON", "FRI"),
//                        "나 이제 6학년이니까 내말 잘 들어라 -도봉초 불은매 김현우");
//
//        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
//        given(studyMapper.studyToStudyNoticeResponseDto(Mockito.any(Study.class))).willReturn(response);
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                get("/study/{study-id}/notice", studyId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
//                .andDo(document(
//                        "study/#30",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.dayOfWeek").type(JsonFieldType.ARRAY).description("요일"),
//                                        fieldWithPath("data.notice").type(JsonFieldType.STRING).description("공지")
//                                )
//                        )
//                ));
//    }
//
//    @DisplayName("#39 - main 스터디 신청 : 버튼이 이미 활성화 되어 있다 가정")
//    @Test
//    @WithMockUser
//    // TODO #39 - main 스터디 신청 : 버튼이 이미 활성화 되어 있다 가정
//    public void registerStudyTest() throws Exception {
//
//    }
//
//    @DisplayName("#26 - main 스터디 신청 수락")
//    @Test
//    @WithMockUser
//    // TODO #26 - main 스터디 신청 수락
//    public void acceptRegisterStudyTest() throws Exception {
//
//    }
//
//    @DisplayName("#27 - main 스터디 신청 거절")
//    @Test
//    @WithMockUser
//    // TODO #27 - main 스터디 신청 거절
//    public void rejectRegisterStudyTest() throws Exception {
//
//    }
//
//    @DisplayName("#28 - 각종 true false 변수들 넘겨주기 (token 값을 사용)")
//    @Test
//    @WithMockUser
//    // TODO #28 - 각종 true false 변수들 넘겨주기 (token 값을 사용)
//    public void getAuthTest() throws Exception {
//
//    }
//
//    @DisplayName("#28 - 각종 true false 변수들 넘겨주기 (token 값 사용 X)")
//    @Test
//    @WithMockUser
//    // TODO #28 - 각종 true false 변수들 넘겨주기 (token 값 사용 X)
//    public void getAuthWithUserIdTest() throws Exception {
//
//    }
//
//    @DisplayName("#29 - studyHall/main 윗부분 header")
//    @Test
//    @WithMockUser
//    // TODO #29 - studyHall/main 윗부분 header
//    //  수정된게 있다고 하니 에러가 날 가능성 큼
//    public void getMainHeaderTest() throws Exception {
//        // given
//        long studyId = 1L;
//        StudyMainDto.HeaderResponse response = new StudyMainDto.HeaderResponse("Your Study", true);
//
//        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
//        given(studyMapper.studyToStudyHeaderResponseDto(Mockito.any(Study.class))).willReturn(response);
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                get("/study/{study-id}/header", studyId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
//                .andDo(document(
//                        "study/#29",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
//                                        fieldWithPath("data.openClose").type(JsonFieldType.BOOLEAN).description("공개/비공개")
//                                )
//                        )
//                ));
//    }
//
//    @DisplayName("#31 - studyHall/main 본문")
//    @Test
//    @WithMockUser
//    // TODO #31 - studyHall/main 본문
//    public void getMainBodyTest() throws Exception {
//        // given
//        long studyId = 1L;
//        StudyMainDto.MainResponse response =
//                new StudyMainDto.MainResponse(
//                        "Your Study",
//                        "집주변 카페에 모여 토론하는 스터디입니다! 가끔 인스타 핫카페에서 친목도 해요",
//                        Arrays.asList("IT", "수학"),
//                        "Our study is an 80-year old coding study...");
//
//        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
//        given(studyMapper.studyToStudyMainResponseDto(Mockito.any(Study.class),Mockito.anyList())).willReturn(response);
//
//        // when
//        ResultActions actions = mockMvc.perform(
//                get("/study/{study-id}/main", studyId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(content().json(gson.toJson(new SingleResponseDto<>(response))))
//                .andDo(document(
//                        "study/#31",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.teamName").type(JsonFieldType.STRING).description("팀이름"),
//                                        fieldWithPath("data.summary").type(JsonFieldType.STRING).description("한줄설명"),
//                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그"),
//                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문")
//                                )
//                        )
//                ));
//    }
//
//    @DisplayName("#33 - studyHall/main 본문 수정")
//    @Test
//    @WithMockUser
//    // TODO #33 - studyHall/main 본문 수정
//    public void patchMainBodyTest() throws Exception {
//
//    }
//
//    @DisplayName("#9 - user의 study 조회")
//    @Test
//    @WithMockUser
//    // TODO #9 - user의 study 조회
//    public void getStudiesByUserTest() throws Exception {
//
//    }
//}
