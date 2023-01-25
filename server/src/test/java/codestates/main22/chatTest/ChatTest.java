package codestates.main22.chatTest;

import codestates.main22.answer.controller.AnswerController;
import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.Answer;
import codestates.main22.answer.mapper.AnswerMapper;
import codestates.main22.answer.service.AnswerService;
import codestates.main22.chat.controller.ChatController;
import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.Chat;
import codestates.main22.chat.mapper.ChatMapper;
import codestates.main22.chat.service.ChatService;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ChatController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
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

    @Test // API 35번 문의 조회
    @WithMockUser
    @DisplayName("#35 - studyHall/main 채팅 조회(아래)")
    public void getChatTest() throws Exception {
        //given
    }

    @Test // API 36번 문의 작성
    @WithMockUser
    @DisplayName("#36 - studyHall/main 채팅 작성(아래)")
    public void postChatTest() throws Exception {
        //given
        long studyId = 1L;

        ChatDto.Post post = new ChatDto.Post("내용", false);
        String content = gson.toJson(post);

        ChatDto.Response response = new ChatDto.Response(1, "유저 이름", "유저 이미지", "내용", false, LocalDateTime.now(), new ArrayList<>());

        given(chatService.findUserByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(chatMapper.chatPostDtoToChat(Mockito.any(ChatDto.Post.class))).willReturn(new Chat());
        given(chatService.createChat(Mockito.anyLong(), Mockito.any(Chat.class), Mockito.any(UserEntity.class))).willReturn(new Chat());
        given(chatMapper.chatToResponseCheck(Mockito.any(Chat.class), Mockito.any(UserEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/chat?studyId={study-Id}", studyId)
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        // then
        actions.andExpect(status().isCreated());
    }
}
