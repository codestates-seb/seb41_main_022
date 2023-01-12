package codestates.main22.tree.controller;

import codestates.main22.dto.SingleResponseDto;
import codestates.main22.tree.dto.TreeDto;
import codestates.main22.tree.entity.Tree;
import codestates.main22.tree.mapper.TreeMapper;
import codestates.main22.tree.service.TreeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tree")
@Validated
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }

    // user의 Tree 조회
    @GetMapping("/user")
    public ResponseEntity getTreeByUserId(HttpServletRequest request) {
        List<Object> treesAndTeamNames = treeService.findTreeByUserId(request);
        List<TreeDto.UserResponse> response = treeMapper.treesToTreeUserResponseDto(treesAndTeamNames);
        return new ResponseEntity<>(new SingleResponseDto<>(new TreeDto.ListResonse<>(response)), HttpStatus.OK);
    }

    // user의 Tree 조회
    @GetMapping
    public ResponseEntity getTreeByStudyId(@Positive @RequestParam long studyId) {
        List<Tree> trees = treeService.findTreeByStudyId(studyId);
        List<TreeDto.StudyResponse> response = treeMapper.treesToTreeStudyResponseDto(trees);
        return new ResponseEntity<>(new SingleResponseDto<>(new TreeDto.ListResonse<>(response)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postTree(@Valid @RequestBody TreeDto.Post requestBody) {
        Tree tree = treeMapper.treePostDtoToTree(requestBody);
        Tree createTree = treeService.createTree(tree);
        TreeDto.UserResponse response = treeMapper.treeToTreeResponseDto(createTree);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{tree-id}")
    public ResponseEntity patchTree(@PathVariable("tree-id") @Positive long treeId,
                                    @Valid @RequestBody TreeDto.Patch requestBody) {
        requestBody.setTreeId(treeId);
        Tree updateTree = treeService.updateTree(treeMapper.treePatchDtoToTree(requestBody));
        TreeDto.UserResponse response =treeMapper.treeToTreeResponseDto(updateTree);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/{tree-id}")
    public ResponseEntity deleteTree(@PathVariable("tree-id") @Positive long treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
