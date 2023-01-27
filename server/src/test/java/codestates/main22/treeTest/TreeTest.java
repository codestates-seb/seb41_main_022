package codestates.main22.treeTest;

import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.tree.controller.TreeController;
import codestates.main22.tree.dto.TreeDto;
import codestates.main22.tree.entity.Tree;
import codestates.main22.tree.mapper.TreeMapper;
import codestates.main22.tree.service.TreeService;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TreeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class TreeTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TreeService treeService;

    @MockBean
    private TreeMapper treeMapper;

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

    @Test // API 11번 유저의 개인 트리 조회 - 완료
    @WithMockUser
    @DisplayName("#11 - user의 Tree 조회")
    public void getUserTreeTest() throws Exception{
        //given
        Tree tree1 = new Tree();
        Study study1 = new Study();
        study1.setTeamName("studyName");
        tree1.setTreeId(1);
        tree1.setTreePoint(0);
        tree1.setTreeImage("나무 이미지");
        tree1.setMakeMonth(1);
        tree1.setCreatedAt(LocalDateTime.now());
        tree1.setModifiedAt(LocalDateTime.now());
        tree1.setStudy(study1);

        Tree tree2 = new Tree();
        Study study2 = new Study();
        study2.setTeamName("스터디 이름");
        tree2.setTreeId(2);
        tree2.setTreePoint(0);
        tree2.setTreeImage("트리 이미지");
        tree2.setMakeMonth(1);
        tree2.setCreatedAt(LocalDateTime.now());
        tree2.setModifiedAt(LocalDateTime.now());
        tree2.setStudy(study2);


        TreeDto.UserResponse response1 = new TreeDto.UserResponse(1, 0, "나무 이미지", LocalDateTime.now(), 1, "studyName" );
        TreeDto.UserResponse response2 = new TreeDto.UserResponse(2, 0, "트리 이미지", LocalDateTime.now(), 1, "스터디 이름");

        List<Object> treesAndTeamNames = List.of(tree1, tree2);
        List<TreeDto.UserResponse> responses = List.of(response1, response2);
        given(treeService.findTreeByUserId(Mockito.any(HttpServletRequest.class))).willReturn(treesAndTeamNames);
        given(treeMapper.treesToTreeUserResponseDto(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/tree/user")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("access-Token", "abc")
                                .header("refresh-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaWRla3FsczkzQGdtYWlsLmNvbSIsImlhdCI6MTY3NDYxMjQwNSwiZXhwIjoxNjc0NjM3NjA1fQ.5T5FoYLpN7Gb0gE6ne7umx3qPvZ8hx5agN1JoG8YusghzqR5FLyjfltoMAg_SW73mieN2zaF6qJpQ9v8c6wBTg")
                );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap()) //리스트로 통과안됨, 맵으로 통과됨 일단 문서화는 됨
                .andDo(document(
                        "tree/#11",
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
                                        fieldWithPath("data.trees").type(JsonFieldType.ARRAY).description("트리 리스트"),
                                        fieldWithPath("data.trees[].treeId").type(JsonFieldType.NUMBER).description("트리 식별자"),
                                        fieldWithPath("data.trees[].treePoint").type(JsonFieldType.NUMBER).description("트리 점수"),
                                        fieldWithPath("data.trees[].treeImage").type(JsonFieldType.STRING).description("트리 이미지 주소"),
                                        fieldWithPath("data.trees[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.trees[].makeMonth").type(JsonFieldType.NUMBER).description("월별 트리"),
                                        fieldWithPath("data.trees[].teamName").type(JsonFieldType.STRING).description("스터디 이름")
                                )
                        )
                ));
    }

    @Test // API 38번 스터디의 트리 조회 - 완료
    @WithMockUser
    @DisplayName("#38 - studyHall/main의 Tree 조회")
    public void getStudyTreeTest() throws Exception {
        //given
        long studyId = 1;

        Tree tree1 = new Tree();
        Study study1 = new Study();
        study1.setStudyId(studyId);
        study1.setTeamName("studyName");
        tree1.setTreeId(1);
        tree1.setTreePoint(0);
        tree1.setTreeImage("나무 이미지");
        tree1.setMakeMonth(1);
        tree1.setCreatedAt(LocalDateTime.now());
        tree1.setModifiedAt(LocalDateTime.now());
        tree1.setStudy(study1);

        Tree tree2 = new Tree();
        Study study2 = new Study();
        study2.setStudyId(studyId);
        study2.setTeamName("스터디 이름");
        tree2.setTreeId(2);
        tree2.setTreePoint(0);
        tree2.setTreeImage("트리 이미지");
        tree2.setMakeMonth(2);
        tree2.setCreatedAt(LocalDateTime.now());
        tree2.setModifiedAt(LocalDateTime.now());
        tree2.setStudy(study2);

        TreeDto.StudyResponse response1 = new TreeDto.StudyResponse(1, 0, "나무 이미지", LocalDateTime.now(), 1);
        TreeDto.StudyResponse response2 = new TreeDto.StudyResponse(2, 0, "트리 이미지", LocalDateTime.now(), 2);

        List<Tree> trees = List.of(tree1, tree2);
        List<TreeDto.StudyResponse> responses = List.of(response1, response2);
        given(treeService.findTreeByStudyId(Mockito.anyLong())).willReturn(trees);
        given(treeMapper.treesToTreeStudyResponseDto(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/tree?studyId={study-id}", studyId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap()) //리스트로 통과안됨, 맵으로 통과됨 일단 문서화는 됨
                .andDo(document(
                        "tree/#38",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("studyId").description("스터디 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.trees").type(JsonFieldType.ARRAY).description("트리 리스트"),
                                        fieldWithPath("data.trees[].treeId").type(JsonFieldType.NUMBER).description("트리 식별자"),
                                        fieldWithPath("data.trees[].treePoint").type(JsonFieldType.NUMBER).description("트리 점수"),
                                        fieldWithPath("data.trees[].treeImage").type(JsonFieldType.STRING).description("트리 이미지 주소"),
                                        fieldWithPath("data.trees[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.trees[].makeMonth").type(JsonFieldType.NUMBER).description("월별 트리")
                                )
                        )
                ));
    }

    @Test // API 49번 (토큰 없이) 유저의 개인 트리 조회 - 완료
    @WithMockUser
    @DisplayName("#49 - (토큰 없이)user의 Tree 조회")
    public void getUserTreeNoTokenTest() throws Exception{
        //given
        Tree tree1 = new Tree();
        Study study1 = new Study();
        study1.setTeamName("studyName");
        tree1.setTreeId(1);
        tree1.setTreePoint(0);
        tree1.setTreeImage("나무 이미지");
        tree1.setMakeMonth(1);
        tree1.setCreatedAt(LocalDateTime.now());
        tree1.setModifiedAt(LocalDateTime.now());
        tree1.setStudy(study1);

        Tree tree2 = new Tree();
        Study study2 = new Study();
        study2.setTeamName("스터디 이름");
        tree2.setTreeId(2);
        tree2.setTreePoint(0);
        tree2.setTreeImage("트리 이미지");
        tree2.setMakeMonth(1);
        tree2.setCreatedAt(LocalDateTime.now());
        tree2.setModifiedAt(LocalDateTime.now());
        tree2.setStudy(study2);


        TreeDto.UserResponse response1 = new TreeDto.UserResponse(1, 0, "나무 이미지", LocalDateTime.now(), 1, "studyName" );
        TreeDto.UserResponse response2 = new TreeDto.UserResponse(2, 0, "트리 이미지", LocalDateTime.now(), 1, "스터디 이름");

        List<Object> treesAndTeamNames = List.of(tree1, tree2);
        List<TreeDto.UserResponse> responses = List.of(response1, response2);
        given(treeService.findTreeByUserIdNoToken(Mockito.anyLong())).willReturn(treesAndTeamNames);
        given(treeMapper.treesToTreeUserResponseDto(Mockito.anyList())).willReturn(responses);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/tree/user")
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap()) //리스트로 통과안됨, 맵으로 통과됨 일단 문서화는 됨
                .andDo(document(
                        "tree/#49",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.trees").type(JsonFieldType.ARRAY).description("트리 리스트"),
                                        fieldWithPath("data.trees[].treeId").type(JsonFieldType.NUMBER).description("트리 식별자"),
                                        fieldWithPath("data.trees[].treePoint").type(JsonFieldType.NUMBER).description("트리 점수"),
                                        fieldWithPath("data.trees[].treeImage").type(JsonFieldType.STRING).description("트리 이미지 주소"),
                                        fieldWithPath("data.trees[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.trees[].makeMonth").type(JsonFieldType.NUMBER).description("월별 트리"),
                                        fieldWithPath("data.trees[].teamName").type(JsonFieldType.STRING).description("스터디 이름")
                                )
                        )
                ));
    }
}
