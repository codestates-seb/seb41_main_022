package codestates.main22.userTest;

import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.dto.StudyRequesterDto;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.controller.UserController;
import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.mapper.UserMapper;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.Token;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;


@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;

    @MockBean
    private Token token;

    @MockBean
    private StudyService studyService;

    @MockBean
    private StudyMapper studyMapper;

    @Autowired
    private Gson gson;

    @Test // API 2번 유저 이미지 조회 - 완료
    @WithMockUser
    @DisplayName("#2 - Header의 user 이미지")
    public void getUserImageTest() throws Exception {
        //given
        String Token = "abc";

        UserEntity user = new UserEntity();
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        user.setEmail("hello@gmail.com");
        user.setInfo("자기소개");
        user.setUsername("test-name");
        user.setUserId(1);
        user.setToken(Token);
        user.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user.setRole(new ArrayList<>());
        user.setUserStudies(new ArrayList<>());

        UserDto.ImageResponse response = new UserDto.ImageResponse("https://avatars.dicebear.com/api/bottts/222.svg");

        given(userService.findUser(Mockito.any(HttpServletRequest.class))).willReturn(user);
        given(userMapper.userEntityToImageResponseCheck(Mockito.any(UserEntity.class))).willReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/user/image")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.imgUrl").value(response.getImgUrl()))
                .andDo(document(
                        "user/#2",
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
                                        fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("이미지 주소")
                                )
                        )
                ));
    }

    @Test // API 8번 유저 정보 일부 조회 - 완료
    @WithMockUser
    @DisplayName("#8 - user의 user 조회")
    public void getUserTest() throws Exception{
        String Token = "abc";

        UserEntity user = new UserEntity();
        user.setCreatedAt(LocalDateTime.now());
        user.setModifiedAt(LocalDateTime.now());
        user.setEmail("hello@gmail.com");
        user.setInfo("자기소개");
        user.setUsername("test-name");
        user.setUserId(1);
        user.setToken(Token);
        user.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user.setRole(new ArrayList<>());
        user.setUserStudies(new ArrayList<>());

        UserDto.SearchUserResponse response = new UserDto.SearchUserResponse(1, "이름", "https://avatars.dicebear.com/api/bottts/222.svg");

        given(userService.findUser(Mockito.any(HttpServletRequest.class))).willReturn(user);
        given(userMapper.userEntityToSearchUserResponse(Mockito.any(UserEntity.class))).willReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                );

        //then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "user/#8",
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
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("이미지 주소")
                                )
                        )
                ));
    }

    @Test // API 12번 유저 이름만 수정 - 완료
    @WithMockUser
    @DisplayName("#12 - user의 user 수정")
    public void patchUserNameTest() throws Exception {
        UserDto.NamePatch patch = new UserDto.NamePatch(1, "유저 이름");
        String content = gson.toJson(patch);
        UserDto.NameResponse response = new UserDto.NameResponse("유저 이름");

        given(userService.findUser(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(userMapper.userNamePatchDtoToUserEntity(Mockito.any(UserDto.NamePatch.class))).willReturn(new UserEntity());
        given(userService.updateUser(Mockito.any(UserEntity.class))).willReturn(new UserEntity());
        given(userMapper.userEntityToNameResponse(Mockito.any(UserEntity.class))).willReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        patch("/user")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "user/#12",
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
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("유저 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저 이름")
                                )
                        )
                ));
    }

    @Test //API 13번 유저 탈퇴 - 완료
    @WithMockUser
    @DisplayName("#13 - user의 user 탈퇴")
    public void deleteUserTest() throws Exception {

        given(userService.findUser(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());

        ResultActions actions = mockMvc.perform(delete("/user").with(csrf()).accept(MediaType.APPLICATION_JSON)
                .header("access-Token", "abc")
                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
        );

        actions.andExpect(status().isNoContent())
                .andDo(document("user/#13",
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ))
                );
    }

    @Test //API 16번 스터디의 멤버 보기 - 완료
    @WithMockUser
    @DisplayName("#16 - studyHall/Community 멤버 보기")
    public void getStudyUsersTest() throws Exception {

        String role1 = "STUDY1_ADMIN";
        String role2 = "STUDY1_USER";

        UserDto.StudyUserResponse response1 = new UserDto.StudyUserResponse("이름A", "https://avatars.dicebear.com/api/bottts/221.svg", role1);
        UserDto.StudyUserResponse response2 = new UserDto.StudyUserResponse("이름B", "https://avatars.dicebear.com/api/bottts/222.svg", role2);

        long studyId = 1L;
        List<UserDto.StudyUserResponse> responses = List.of(response1, response2);

        given(userService.findByStudy(Mockito.anyLong())).willReturn(new ArrayList<>());
        given(userMapper.usersToStudyUserResponse(Mockito.anyList(), Mockito.anyString())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/user/study")
                                .param("studyId", String.valueOf(studyId))
                                .accept(MediaType.APPLICATION_JSON)
                );
        // then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "user/#16",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data[].imgUrl").type(JsonFieldType.STRING).description("이미지 주소"),
                                        fieldWithPath("data[].role").type(JsonFieldType.STRING).description("유저 권한")
                                )
                        )
                ));
    }

    //    @Test //API 25번 스터디 신청자 보기 - 완료
    @Test
    @WithMockUser
    @DisplayName("#25 - studyHall/setting 스터디 신청 내역")
    public void getRequester() throws Exception {
        //given
        String Token1 = "abc";

        UserEntity user1 = new UserEntity();
        user1.setCreatedAt(LocalDateTime.now());
        user1.setModifiedAt(LocalDateTime.now());
        user1.setEmail("hello@gmail.com");
        user1.setInfo("자기소개");
        user1.setUsername("test-name");
        user1.setUserId(1);
        user1.setToken(Token1);
        user1.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user1.setRole(new ArrayList<>());
        user1.setUserStudies(new ArrayList<>());

        String Token2 = "abcd";

        UserEntity user2 = new UserEntity();
        user2.setCreatedAt(LocalDateTime.now());
        user2.setModifiedAt(LocalDateTime.now());
        user2.setEmail("hello@gmail.com");
        user2.setInfo("자기소개");
        user2.setUsername("test-name");
        user2.setUserId(1);
        user2.setToken(Token2);
        user2.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user2.setRole(new ArrayList<>());
        user2.setUserStudies(new ArrayList<>());

        long studyId = 1L;

        StudyRequesterDto.Response response = new StudyRequesterDto.Response(new ArrayList<>());
        List<UserEntity> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        List<UserDto.SearchUserResponse> responses = new ArrayList<>();
        UserDto.SearchUserResponse response1 = new UserDto.SearchUserResponse(1, "유저A", "이미지A");
        UserDto.SearchUserResponse response2 = new UserDto.SearchUserResponse(2, "유저B", "이미지B");
        responses.add(response1);
        responses.add(response2);

        given(studyService.findStudy(Mockito.anyLong())).willReturn(new Study());
        given(token.findByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(studyMapper.studyToStudyRequesterResponseDto(Mockito.any(Study.class))).willReturn(response);
        given(userService.findRequester(Mockito.any(StudyRequesterDto.Response.class))).willReturn(userList);
        given(userMapper.userEntityToSearchUsersResponse(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/user/{study-id}/requester", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                );

        //then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "user/#25",
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
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data[].imgUrl").type(JsonFieldType.STRING).description("이미지 주소")
                                )
                        )
                ));
    }

}