package codestates.main22.tag.controller;

import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.mapper.TagMapper;
import codestates.main22.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
        Tag tag = tagService.createTag(tagMapper.tagReqPostDtoToTag(post));
        TagResponseDto.Post response = tagMapper.tagToTagResPostDto(tag);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    @GetMapping("/{tag-id}")
    public ResponseEntity getTag(@PathVariable("tag-id") @Positive long tagId) {
        Tag tag = tagService.findTag(tagId);
        TagResponseDto.Post response = tagMapper.tagToTagResPostDto(tag);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getTags(@Positive @RequestParam int page,
                                      @Positive @RequestParam int size) {
        Page<Tag> pageTags = tagService.findTags(page - 1, size);
        List<Tag> tags = pageTags.getContent();
        List<TagResponseDto.Post> responses = tagMapper.tagsToTagResPostDtos(tags);

        return new ResponseEntity<>(
                new MultiResponseDto<>(responses, pageTags),
                HttpStatus.OK
        );
    }

    @PatchMapping("/{tag-id}")
    public ResponseEntity patchTag(@Positive @PathVariable("tag-id") long tagId,
                                       @Valid @RequestBody TagRequestDto.Patch patch) {
        patch.setTagId(tagId);
        Tag tag = tagService.updateTag(tagMapper.tagReqPatchDtoToTag(patch));
        TagResponseDto.Post response = tagMapper.tagToTagResPostDto(tag);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @DeleteMapping("/{tag-id}")
    public ResponseEntity deleteTag(@Positive @PathVariable("tag-id") long tagId) {
        tagService.deleteTag(tagId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}