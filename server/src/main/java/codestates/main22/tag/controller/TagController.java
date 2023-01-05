package codestates.main22.tag.controller;

import codestates.main22.tag.dto.TagReponseDto;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.mapper.TagMapper;
import codestates.main22.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/tag")
@Valid
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    public TagController(TagService tagService, TagMapper tagMapper) {
        this.tagService = tagService;
        this.tagMapper = tagMapper;
    }

    @PostMapping
    public ResponseEntity postTag(@Valid @RequestBody TagRequestDto.Post post) {
        Tag tag = tagService.createTag(tagMapper.tagPostDtoToTag(post));
        TagReponseDto.Post response = tagMapper.tagToTagPostDto(tag);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}