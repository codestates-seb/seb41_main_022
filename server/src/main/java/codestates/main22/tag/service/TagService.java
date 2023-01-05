package codestates.main22.tag.service;

import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // 캘린더 생성
    public Tag createTag(Tag tag) {
        return new Tag();
    }
}
