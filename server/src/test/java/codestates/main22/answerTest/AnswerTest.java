package codestates.main22.answerTest;

import codestates.main22.answer.controller.AnswerController;
import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.Answer;
import codestates.main22.answer.mapper.AnswerMapper;
import codestates.main22.answer.service.AnswerService;
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
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class AnswerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper answerMapper;

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

    @Test // API 37번 답변 작성
    @WithMockUser
    @DisplayName("#37 - studyHall/main 댓글 작성(아래)")
    public void postAnswerTest() throws Exception {
        //given
        long chatId = 1L;

        AnswerDto.Post post = new AnswerDto.Post("내용");
        String content = gson.toJson(post);

        AnswerDto.Response response = new AnswerDto.Response(1, "유저 이름", "유저 이미지", "내용", LocalDateTime.now());

        given(answerService.findUserByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(answerMapper.answerPostDtoToAnswer(Mockito.any(AnswerDto.Post.class))).willReturn(new Answer());
        given(answerService.createAnswer(Mockito.anyLong(), Mockito.any(Answer.class), Mockito.any(UserEntity.class))).willReturn(new Answer());
        given(answerMapper.answerToResponseCheck(Mockito.any(Answer.class), Mockito.any(UserEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/answer/{chat-Id}", chatId)
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content));

        // then
        actions.andExpect(status().isCreated());
    }
}
