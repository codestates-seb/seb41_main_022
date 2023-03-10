package codestates.main22.message;

import codestates.main22.message.controller.MessageController;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.dto.MessageResponseDto;
import codestates.main22.message.entity.Message;
import codestates.main22.message.mapper.MessageMapper;
import codestates.main22.message.service.MessageService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.util.JwtMockBean;
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

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
public class MessageTest extends JwtMockBean {

    @MockBean
    private MessageService messageService;

    @MockBean
    private MessageMapper messageMapper;

    @Test // API 14??? ????????? ?????? - ??????
    @WithMockUser
    @DisplayName("#14 - studyHall/Community ?????? ??????")
    public void postMessageTest() throws Exception {
        //given
        long studyId = 1L;

        MessageRequestDto.Post post = new MessageRequestDto.Post("??????", LocalDateTime.now());
        String content = gson.toJson(post);

        MessageResponseDto.UserResponse response = new MessageResponseDto.UserResponse("??????", LocalDateTime.now(), 1, "?????? ??????", "https://avatars.dicebear.com/api/bottts/222.svg");

        given(messageService.findUserByToken(Mockito.any(HttpServletRequest.class))).willReturn(new UserEntity());
        given(messageMapper.messageReqPostDtoToMessage(Mockito.any(MessageRequestDto.Post.class))).willReturn(new Message());
        given(messageService.createMessage(Mockito.anyLong(), Mockito.any(Message.class), Mockito.any(UserEntity.class))).willReturn(new Message());
        given(messageMapper.messageToMessageUserResponseDto(Mockito.any(Message.class), Mockito.any(UserEntity.class))).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/message?studyId={study-id}", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                                .content(content));

        // then
        actions.andExpect(status().isCreated())
                .andDo(document(
                        "message/#14",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("????????? ?????????")
                        ),
                        requestHeaders(
                                List.of(
                                        headerWithName("access-Token").description("access ??????"),
                                        headerWithName("refresh-Token").description("refresh ??????")
                                )
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("dateTime").type(JsonFieldType.STRING).description("?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data.dateTime").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.messageUserId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.imgUrl").type(JsonFieldType.STRING).description("?????? ????????? ??????")
                                )
                        )
                ));
    }

    @Test // API 15??? ????????? ?????? - ??????
    @WithMockUser
    @DisplayName("#15 - studyHall/Community ?????? ??????")
    public void getMessageTest() throws Exception {
        //given
        long studyId = 1L;

        MessageResponseDto.UserResponse userResponse1 = new MessageResponseDto.UserResponse("??????", LocalDateTime.now(), 1, "?????? ??????", "?????? ????????? ??????");
        MessageResponseDto.UserResponse userResponse2 = new MessageResponseDto.UserResponse("??????", LocalDateTime.now(), 2, "?????? ??????", "?????? ????????? ??????");
        List<MessageResponseDto.UserResponse> responses = List.of(userResponse1, userResponse2);

        given(messageService.findByStudyMessage(Mockito.anyLong())).willReturn(new ArrayList<>());
        given(messageService.findUsers(Mockito.anyList())).willReturn(new HashMap<>());
        given(messageMapper.messagesToMessageUserResponse(Mockito.anyList(), Mockito.any(HashMap.class))).willReturn(responses);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/message?studyId={study-id}", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );
        // then
        actions.andExpect(status().isOk())
                .andDo(document(
                        "message/#15",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("????????? ?????????")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("??????"),
                                        fieldWithPath("data[].dateTime").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].messageUserId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].imgUrl").type(JsonFieldType.STRING).description("?????? ????????? ??????")
                                )
                        )
                ));
    }
}
