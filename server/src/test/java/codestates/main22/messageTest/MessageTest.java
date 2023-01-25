package codestates.main22.messageTest;

import codestates.main22.message.controller.MessageController;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.dto.MessageResponseDto;
import codestates.main22.message.entity.Message;
import codestates.main22.message.mapper.MessageMapper;
import codestates.main22.message.service.MessageService;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.tree.controller.TreeController;
import codestates.main22.user.controller.UserController;
import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.mapper.UserMapper;
import codestates.main22.user.service.UserService;
import codestates.main22.utils.Token;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpServletRequest;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(MessageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MessageTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private MessageMapper messageMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private CustomAuthorityUtils customAuthorityUtils;

    @MockBean
    private Token token;

    @Autowired
    private Gson gson;

    @Test // API 14번 메세지 작성 - 400 에러 발생 중
    @WithMockUser
    @DisplayName("#14 - studyHall/Community 채팅 작성")
    public void postMessageTest() throws Exception {
        //given
        long studyId = 1L;

        MessageRequestDto.Post post = new MessageRequestDto.Post("내용", LocalDateTime.now());
        String content = gson.toJson(post);

        MessageResponseDto.UserResponse response = new MessageResponseDto.UserResponse("내용", LocalDateTime.now(), 1, "유저 이름", "https://avatars.dicebear.com/api/bottts/222.svg");

        given(messageService.findUserByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(messageMapper.messageReqPostDtoToMessage(Mockito.any(MessageRequestDto.Post.class))).willReturn(new Message());
        given(messageService.createMessage(Mockito.anyLong(), Mockito.any(Message.class), Mockito.any(HttpServletRequest.class))).willReturn(new Message());
        given(messageMapper.messageToMessageUserResponseDto(Mockito.any(Message.class), Mockito.any(UserEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/message?studyId={study-id}", studyId)
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        // then
        actions.andExpect(status().isCreated());
    }

    @Test // API 15번 메세지 보기 given 부분 작성 어떻게 해야할지 고민 중
    @WithMockUser
    @DisplayName("#15 - studyHall/Community 채팅 보기")
    public void getMessageTest() throws Exception {
        //given
        Message message1 = new Message();
        UserEntity user1 = new UserEntity();
        Study study = new Study();
        user1.setUserId(1);
        user1.setUsername("유저 이름A");
        message1.setMessageId(1);
        message1.setMessageUserId(user1.getUserId());
        message1.setStudy(study);
        message1.setUserName(user1.getUsername());
        message1.setContent("메세지 내용");
        message1.setDateTime(LocalDateTime.now());
        message1.setCreatedAt(LocalDateTime.now());
        message1.setModifiedAt(LocalDateTime.now());

        Message message2 = new Message();
        UserEntity user2 = new UserEntity();
        user2.setUserId(2);
        user2.setUsername("유저 이름B");
        message1.setMessageId(2);
        message1.setMessageUserId(user2.getUserId());
        message1.setStudy(study);
        message1.setUserName(user2.getUsername());
        message1.setContent("메세지 내용");
        message1.setDateTime(LocalDateTime.now());
        message1.setCreatedAt(LocalDateTime.now());
        message1.setModifiedAt(LocalDateTime.now());
    }
}
