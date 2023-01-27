package codestates.main22.tag;

import codestates.main22.tag.controller.TagController;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.mapper.TagMapper;
import codestates.main22.tag.service.TagService;
import codestates.main22.util.JwtMockBean;
import codestates.main22.utils.Init;
import org.junit.jupiter.api.BeforeAll;
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

import static codestates.main22.util.ApiDocumentUtils.getDocumentRequest;
import static codestates.main22.util.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(TagController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
public class TagControllerRestDocsTest extends JwtMockBean {
    private static List<String> allTagList;
    private static List<String> tagList;

    @MockBean
    private TagService tagService;

    @MockBean
    private TagMapper tagMapper;

    @BeforeAll
    public static void initAll() {
        allTagList = Init.tagList
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));

        tagList = List.of(allTagList.get(0), allTagList.get(1))
                .stream()
                .collect(Collectors.toCollection(ArrayList::new));

        startWithUrl = "/tag";
    }

    @DisplayName("#5 - home의 태그 리스트(전체 호출)")
    @WithMockUser
    @Test
    public void getTagsTest() throws Exception {
        // given
        TagResponseDto.Get response = new TagResponseDto.Get(allTagList);

        given(tagService.findTags()).willReturn(new ArrayList<>());

        given(tagMapper.tagsToTagResGetDtos(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get(startWithUrl)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tags").value(response.getTags()))
                .andDo(document("tag/#5",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그 리스트")
                                )
                        )
                ));
    }

    @DisplayName("#10 : user의 tag 조회")
    @WithMockUser
    @Test
    public void getTagByUserIdTest() throws Exception {
        // given
        TagResponseDto.Get response = new TagResponseDto.Get(tagList);

        given(tagService.findTags()).willReturn(new ArrayList<>());

        given(tagMapper.tagsToTagResGetDtos(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get(startWithUrl + "/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tags").value(response.getTags()))
                .andDo(document("tag/#10",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그 리스트")
                                )
                        )
                ));
    }

    @DisplayName("#32 : studyHall/main tag 조회")
    @WithMockUser
    @Test
    public void getTagByStudyIdTest() throws Exception {
        // given
        long studyId = 1L;

        TagResponseDto.Get response = new TagResponseDto.Get(tagList);

        given(tagService.findTags()).willReturn(new ArrayList<>());

        given(tagMapper.tagsToTagResGetDtos(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get(startWithUrl + "/study/{study-id}", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tags").value(response.getTags()))
                .andDo(document("tag/#32",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그 리스트")
                                )
                        )
                ));
    }

    @DisplayName("#34 : studyHall/main tag 수정")
    @WithMockUser
    @Test
    public void patchTagByStudyIdTest() throws Exception {
        // given
        long studyId = 1L;
        TagRequestDto.Post post = new TagRequestDto.Post();
        post.tags = tagList;
        String content = gson.toJson(post);
        TagResponseDto.Get response = new TagResponseDto.Get(tagList);

        given(tagService.findTags()).willReturn(new ArrayList<>());

        given(tagMapper.tagsToTagResGetDtos(Mockito.anyList())).willReturn(response);

        // when
        ResultActions actions =
                mockMvc.perform(
                        patch(startWithUrl + "/study/{study-id}", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.tags").value(response.getTags()))
                .andDo(document("tag/#34",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("study-id").description("스터디 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("tags").type(JsonFieldType.ARRAY).description("태그 리스트")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("태그 리스트")
                                )
                        )
                ));
    }
}
//    @DisplayName("")
//    @WithMockUser
//    @Test
//    public void getTagByUserIdTest() throws Exception {
//        // given
//        // when
//        // then
//    }
