package codestates.main22.tag.controller;

import codestates.main22.dto.SingleResponseDto;
import codestates.main22.tag.dto.TagRequestDto;
import codestates.main22.tag.dto.TagResponseDto;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.mapper.TagMapper;
import codestates.main22.tag.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

        tagService.saveTags();
    }

    // Create New Study
    @PostMapping("/study/{study-id}")
    public ResponseEntity postTag(@PathVariable("study-id") @Positive long studyId,
                                  @Valid @RequestBody TagRequestDto.Post post) {
        List<Tag> tags = tagService.createTagStudies(studyId, post.getTags());
        TagResponseDto.Get response = tagMapper.tagsToTagResGetDtos(tags);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    // home의 태그 리스트(전체 호출)
    @GetMapping
    public ResponseEntity getTags() {
        List<Tag> tags = tagService.findTags();
        TagResponseDto.Get response = tagMapper.tagsToTagResGetDtos(tags);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    // user의 tag 조회
    @GetMapping("/user")
    public ResponseEntity getTagByUserId(HttpServletRequest request) {
        List<Tag> tags = tagService.findTagsByUserId(request);
        TagResponseDto.Get response = tagMapper.tagsToTagResGetDtos(tags);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    // studyHall/main tag 조회
    @GetMapping("/study/{study-id}")
    public ResponseEntity getTagByStudyId(@PathVariable("study-id") @Positive long studyId) {
        List<Tag> tags = tagService.findTagsByStudyId(studyId);
        TagResponseDto.Get response = tagMapper.tagsToTagResGetDtos(tags);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    // studyHall/main tag 수정
    @PatchMapping("/study/{study-id}")
    public ResponseEntity patchTagByStudyId(@Positive @PathVariable("study-id") long studyId,
                                            @Valid @RequestBody TagRequestDto.Post post,
                                            HttpServletRequest request) {
        List<Tag> tags = tagService.updateTag(studyId, post.getTags(), request);
        TagResponseDto.Get response = tagMapper.tagsToTagResGetDtos(tags);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity postTag(@Valid @RequestBody TagRequestDto.Post post) {
        Tag tag = tagService.createTag(tagMapper.tagReqPostDtoToTag(post));
        TagResponseDto.Post response = tagMapper.tagToTagResPostDto(tag);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    // tagId를 이용해서 태그 호출
//    @GetMapping("/{tag-id}")
//    public ResponseEntity getTag(@PathVariable("tag-id") @Positive long tagId) {
//        Tag tag = tagService.findTag(tagId);
//        TagResponseDto.Post response = tagMapper.tagToTagResPostDto(tag);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(response), HttpStatus.OK
//        );
//    }

    // 페이지네이션
//    @GetMapping
//    public ResponseEntity getTags(@Positive @RequestParam int page,
//                                      @Positive @RequestParam int size) {
//        Page<Tag> pageTags = tagService.findTags(page - 1, size);
//        List<Tag> tags = pageTags.getContent();
//        List<TagResponseDto.Post> responses = tagMapper.tagsToTagResPostDtos(tags);
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(responses, pageTags),
//                HttpStatus.OK
//        );
//    }

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