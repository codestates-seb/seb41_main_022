package codestates.main22.userTest;

import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.controller.UserController;
import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.mapper.UserMapper;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.Token;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


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

    @Test // API 2번 유저 이미지 조회 302 or 리스폰스가 로그인 화면 리다이렉션?
    @WithMockUser
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

        given(userService.verifiedUser(Mockito.anyInt())).willReturn(user);
        given(userMapper.userEntityToImageResponseCheck(Mockito.any(UserEntity.class))).willReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/user/image")
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions.andExpect(status().isOk());
//                .andDo(document(
//                        "get-userImage",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("이미지 주소")
//                                )
//                        )
//                ));
    }

    @Test // API 8번 유저 정보 일부 조회 302 or 리스폰스가 로그인 화면 리다이렉션?
    @WithMockUser
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

        given(userService.verifiedUser(Mockito.anyInt())).willReturn(user);
        given(userMapper.userEntityToSearchUserResponse(Mockito.any(UserEntity.class))).willReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/study/user")
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions.andExpect(status().isOk());
//        actions.andExpect(redirectedUrl("http://localhost:8080/oauth2/authorization/google"));
    }

    @Test // API 12번 유저 이름만 수정 403에러
    public void patchUserNameTest() throws Exception {
        UserDto.NamePatch patch = new UserDto.NamePatch(1, "유저 이름");
        String content = gson.toJson(patch);
        UserDto.NameResponse response = new UserDto.NameResponse("유저 이름");

        given(userMapper.userNamePatchDtoToUserEntity(Mockito.any(UserDto.NamePatch.class))).willReturn(new UserEntity());
        given(userService.updateUser(Mockito.any(UserEntity.class))).willReturn(new UserEntity());
        given(userMapper.userEntityToNameResponse(Mockito.any(UserEntity.class))).willReturn(response);

        //when
        ResultActions actions =
                mockMvc.perform(
                        patch("/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk());
    }

    @Test //API 13번 유저 탈퇴 403에러
    public void deleteUserTest() throws Exception {
        ResultActions actions = mockMvc.perform(delete("/user").accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNoContent());
    }

    @Test //API 16번 스터디의 멤버 보기 302 or 리스폰스가 로그인 화면 리다이렉션?
    public void getStudyUsersTest() throws Exception {
        String Token1 = "abc";

        UserEntity user1 = new UserEntity();
        user1.setCreatedAt(LocalDateTime.now());
        user1.setModifiedAt(LocalDateTime.now());
        user1.setEmail("hello1@gmail.com");
        user1.setInfo("자기소개1");
        user1.setUsername("test-name1");
        user1.setUserId(1);
        user1.setToken(Token1);
        user1.setImgUrl("https://avatars.dicebear.com/api/bottts/221.svg");
        user1.setRole(new ArrayList<>());
        user1.setUserStudies(new ArrayList<>());

        String Token2 = "abcd";

        UserEntity user2 = new UserEntity();
        user2.setCreatedAt(LocalDateTime.now());
        user2.setModifiedAt(LocalDateTime.now());
        user2.setEmail("hello2@gmail.com");
        user2.setInfo("자기소개2");
        user2.setUsername("test-name2");
        user2.setUserId(2);
        user2.setToken(Token2);
        user2.setImgUrl("https://avatars.dicebear.com/api/bottts/222.svg");
        user2.setRole(new ArrayList<>());
        user2.setUserStudies(new ArrayList<>());

        UserDto.StudyUserResponse response1 = new UserDto.StudyUserResponse("이름A", "https://avatars.dicebear.com/api/bottts/221.svg", "");
        UserDto.StudyUserResponse response2 = new UserDto.StudyUserResponse("이름B", "https://avatars.dicebear.com/api/bottts/222.svg", "");

        long studyId = 1L;
        List<UserEntity> users =new ArrayList<>();
        List<UserDto.StudyUserResponse> responses = List.of(response1, response2);

        given(userService.findByStudy(Mockito.anyLong())).willReturn(users);
        given(userMapper.usersToStudyUserResponse(Mockito.anyList(), Mockito.anyString())).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/user/study")
                                .param("study-id", String.valueOf(studyId))
                                .accept(MediaType.APPLICATION_JSON)
                );
        // then
//        actions.andExpect(status().isOk());
        actions.andExpect(redirectedUrl("http://localhost:8080/oauth2/authorization/google"));
    }

//    @Test //API 25번 스터디 신청자 보기
}
