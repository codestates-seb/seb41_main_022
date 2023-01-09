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

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/v1/tree")
@Validated
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;

    public TreeController(TreeService treeService, TreeMapper treeMapper) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
    }

    @PostMapping
    public ResponseEntity postTree(@Valid @RequestBody TreeDto.Post requestBody) {
        Tree tree = treeMapper.treePostDtoToTree(requestBody);
        Tree createTree = treeService.createTree(tree);
        TreeDto.Response response = treeMapper.treeToTreeResponseDto(createTree);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{tree-id}")
    public ResponseEntity patchTree(@PathVariable("tree-id") @Positive long treeId,
                                    @Valid @RequestBody TreeDto.Patch requestBody) {
        requestBody.setTreeId(treeId);
        Tree updateTree = treeService.updateTree(treeMapper.treePatchDtoToTree(requestBody));
        TreeDto.Response response =treeMapper.treeToTreeResponseDto(updateTree);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{tree-id}")
    public ResponseEntity getTree(@PathVariable("tree-id") @Positive long treeId) {
        Tree findTree = treeService.findTree(treeId);
        TreeDto.Response response = treeMapper.treeToTreeResponseDto(findTree);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @DeleteMapping("/{tree-id}")
    public ResponseEntity deleteTree(@PathVariable("tree-id") @Positive long treeId) {
        treeService.deleteTree(treeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
