package codestates.main22.tree.controller;

import codestates.main22.tree.mapper.TreeMapper;
import codestates.main22.tree.service.TreeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tree")
@Validated
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper mapper;

    public TreeController(TreeService treeService, TreeMapper mapper) {
        this.treeService = treeService;
        this.mapper = mapper;
    }
}
