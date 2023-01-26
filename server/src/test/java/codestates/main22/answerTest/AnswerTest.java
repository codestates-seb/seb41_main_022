package codestates.main22.answerTest;

import codestates.main22.answer.controller.AnswerController;
import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.answer.entity.Answer;
import codestates.main22.answer.mapper.AnswerMapper;
import codestates.main22.answer.service.AnswerService;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
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

    @Test // API 37번 답변 작성 - 완료 but 문서화(리스폰스)에서 answerCreatedAt 타입 확인 필요
    @WithMockUser
    @DisplayName("#37 - studyHall/main 댓글 작성(아래)")
    public void postAnswerTest() throws Exception {
        //given
        long chatId = 1L;

        AnswerDto.Post post = new AnswerDto.Post("내용");
        String content = gson.toJson(post);

        AnswerDto.Response response = new AnswerDto.Response(1, 1, "유저 이름", "유저 이미지", "내용", LocalDateTime.now());

        Answer answer = new Answer();
        answer.setCreatedAt(LocalDateTime.now().withNano(0));

        given(answerService.findUserByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(answerMapper.answerPostDtoToAnswer(Mockito.any(AnswerDto.Post.class))).willReturn(new Answer());
        given(answerService.createAnswer(Mockito.anyLong(), Mockito.any(Answer.class), Mockito.any(UserEntity.class))).willReturn(answer);
        given(answerMapper.answerToResponseCheck(Mockito.any(Answer.class), Mockito.any(UserEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/answer/{chat-Id}", chatId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                                .content(content));

        // then
        actions.andExpect(status().isCreated())
                .andDo(document(
                        "answer/#37",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("chat-Id").description("질문 식별자")
                        ),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.answerUserId").type(JsonFieldType.NUMBER).description("답변 작성자 식별자"),
                                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("유저 이름"),
                                        fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("유저 이미지 주소"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("data.answerCreatedAt").type(JsonFieldType.STRING).description("작성 시간")
                                )
                        )
                ));
    }

    @Test //API 46번 답변 삭제 - 완료
    @WithMockUser
    @DisplayName("#46 - studyHall/main 댓글 삭제(아래)")
    public void deleteAnswerTest() throws Exception {
        long answerId = 1L;

        given(token.findByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(answerService.findAnswer(Mockito.anyLong())).willReturn(new Answer());

        ResultActions actions = mockMvc.perform(delete("/answer/{answr-id}", answerId).with(csrf()).accept(MediaType.APPLICATION_JSON)
                .header("access-Token", "abc")
                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
        );

        actions.andExpect(status().isNoContent())
                .andDo(document("answer/#46",
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access 토큰"),
                                        headerWithName("refresh-Token").description("refresh 토큰")
                                )
                        ))
                );
    }
}
