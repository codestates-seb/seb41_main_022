package codestates.main22.tag.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // 태그 생성
    @Transactional
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // 태그 수정
    public Tag updateTag(Tag tag) {
        Tag findTag = verifiedTag(tag.getTagId());

        Optional.ofNullable(tag.getName())
                .ifPresent(name -> findTag.setName(name));

        return tagRepository.save(findTag);
    }

    // 태그 삭제
    public void deleteTag(long tagId) {
        Tag tag = verifiedTag(tagId);
        tagRepository.delete(tag);
    }

    // 태그 조회
    public Tag findTag(long tagId) {
        return verifiedTag(tagId);
    }

    // 태그 전체 조회
    public Page<Tag> findTags(int page, int size) {
        return tagRepository.findAll(PageRequest.of(page, size,
                Sort.by("tagId").descending()));
    }

    // 태그 증명
    public Tag verifiedTag(long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        Tag tag =
                optionalTag.orElseThrow(() ->
                        new BusinessLogicException((ExceptionCode.MESSAGE_NOT_FOUND)));

        return tag;
    }
}
