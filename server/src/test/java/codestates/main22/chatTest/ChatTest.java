package codestates.main22.chatTest;

import codestates.main22.answer.entity.Answer;
import codestates.main22.answer.service.AnswerService;
import codestates.main22.chat.controller.ChatController;
import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.Chat;
import codestates.main22.chat.mapper.ChatMapper;
import codestates.main22.chat.service.ChatService;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.Token;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ChatController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
public class ChatTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @MockBean
    private ChatMapper chatMapper;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private UserService userService;


    @MockBean
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;

    @MockBean
    private Token token;

    @MockBean
    private StudyService studyService;

    @Autowired
    private Gson gson;

    @Test // API 35번 채팅 조회
    @WithMockUser
    @DisplayName("#35 - studyHall/main 채팅 조회(아래)")
    public void getChatTest() throws Exception {
        //given
        long studyId = 1L;
        int page = 2;
        int size = 5;

        Chat chat1 = new Chat(1, 1, "내용", false, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new Study());
        Chat chat2 = new Chat(2, 2, "내용", false, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new Study());

        Page<Chat> chats = new PageImpl<>(List.of(chat1, chat2), PageRequest.of(page-1, size,
                Sort.by("chatId").descending()), 2);

        ChatDto.Response response1 = new ChatDto.Response(1, 1, "유저A", "유저 이미지 주소", "내용", false, LocalDateTime.now(), new ArrayList<>());
        ChatDto.Response response2 = new ChatDto.Response(2, 2, "유저B", "유저 이미지 주소", "내용", false, LocalDateTime.now(), new ArrayList<>());
        List<ChatDto.Response> responses = List.of(response1, response2);

        given(chatService.findByStudy(Mockito.anyLong(), Mockito.anyInt(), Mockito.anyInt())).willReturn(chats);
        given(chatService.filterByIsClosedChat(Mockito.anyList(), Mockito.any(HttpServletRequest.class))).willReturn(new ArrayList<>());
        given(chatService.findUsers(Mockito.anyList())).willReturn(new HashMap<>());
        given(chatMapper.chatsToResponse(Mockito.anyList(), Mockito.any(HashMap.class))).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/chat/{study-id}?page=1&size=5", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg"));

        // then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "chat/#35",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈")
                        ),
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
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].chatId").type(JsonFieldType.NUMBER).description("채팅 식별자"),
                                        fieldWithPath("data[].chatUserId").type(JsonFieldType.NUMBER).description("채팅 작성자 식별자"),
                                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data[].imgUrl").type(JsonFieldType.STRING).description("유저 이미지 주소"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("data[].isClosedChat").type(JsonFieldType.BOOLEAN).description("비밀 채팅"),
                                        fieldWithPath("data[].chatCreatedAt").type(JsonFieldType.STRING).description("작성 시간"),
                                        fieldWithPath("data[].answers").type(JsonFieldType.ARRAY).description("문의 답변"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 수"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("페이지 요소 수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수")
                                )
                        )
                ));
    }

    @Test // API 36번 채팅 작성 - 완료
    @WithMockUser
    @DisplayName("#36 - studyHall/main 채팅 작성(아래)")
    public void postChatTest() throws Exception {
        //given
        long studyId = 1L;

        ChatDto.Post post = new ChatDto.Post("내용", false);
        String content = gson.toJson(post);

        ChatDto.Response response = new ChatDto.Response(1, 1, "유저 이름", "유저 이미지", "내용", false, LocalDateTime.now(), new ArrayList<>());

        given(chatService.findUserByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(chatMapper.chatPostDtoToChat(Mockito.any(ChatDto.Post.class))).willReturn(new Chat());
        given(chatService.createChat(Mockito.anyLong(), Mockito.any(Chat.class), Mockito.any(UserEntity.class))).willReturn(new Chat());
        given(chatMapper.chatToResponseCheck(Mockito.any(Chat.class), Mockito.any(UserEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/chat?studyId={study-Id}", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                                .content(content));

        // then
        actions.andExpect(status().isCreated())
                .andDo(document(
                        "chat/#36",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("스터디 식별자")
                        ),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("isClosedChat").type(JsonFieldType.BOOLEAN).description("비밀 채팅")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.chatId").type(JsonFieldType.NUMBER).description("채팅 식별자"),
                                        fieldWithPath("data.chatUserId").type(JsonFieldType.NUMBER).description("채팅 작성자 식별자"),
                                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("유저 이미지 주소"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("data.isClosedChat").type(JsonFieldType.BOOLEAN).description("비밀 채팅"),
                                        fieldWithPath("data.chatCreatedAt").type(JsonFieldType.STRING).description("작성 시간"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("문의 답변")
                                )
                        )
                ));
    }

    @Test //API 45번 채팅 삭제 - 완료
    @WithMockUser
    @DisplayName("#45 - studyHall/main 채팅 삭제(아래)")
    public void deleteChatTest() throws Exception {
        long chatId = 1L;

        given(token.findByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(chatService.findChat(Mockito.anyLong())).willReturn(new Chat());

        ResultActions actions = mockMvc.perform(delete("/chat/{chat-id}", chatId).with(csrf()).accept(MediaType.APPLICATION_JSON)
                .header("access-Token", "abc")
                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
        );

        actions.andExpect(status().isNoContent())
                .andDo(document("chat/#45",
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ))
                );
    }
}
