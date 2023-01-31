package codestates.main22.tag.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.entity.TagStudy;
import codestates.main22.tag.repository.TagRepository;
import codestates.main22.tag.repository.TagStudyRepository;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.utils.Init;
import codestates.main22.utils.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagStudyRepository tagStudyRepository;
    private final StudyRepository studyRepository;
    private final Token token;

    public TagService(TagRepository tagRepository,
                      TagStudyRepository tagStudyRepository,
                      StudyRepository studyRepository,
                      Token token) {
        this.tagRepository = tagRepository;
        this.tagStudyRepository = tagStudyRepository;
        this.studyRepository = studyRepository;
        this.token = token;
    }

    // 태그 생성
    @Transactional
    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    // Study와 연관된 tagStudies 생성
    public List<Tag> createTagStudies(Study study, List<String> names) {
//        Study study = studyRepository.findById(studyId).get();
        List<Tag> tags = makeListTags(names);

        for(Tag tag : tags) {
            TagStudy tagStudy = new TagStudy();
            tagStudy.setStudy(study);
            tagStudy.setTag(tag);
        }

        return tags;
    }

    // 태그 수정 By Study-Id
    public List<Tag> updateTag(long studyId, List<String> names, HttpServletRequest request) {
        // 스터디장 권한이 있는지 확인
        UserEntity user = token.checkStudyAdmin(request, studyId);

        // 스터디 조회
        Study study = studyRepository.findById(studyId).get();

        List<Tag> before = findTagsByStudyId(studyId);
        List<Tag> after = makeListTags(names);

        // before을 기준으로 정렬
        for(Tag tag : before) {
            // 삭제될 태그
            if(!after.contains(tag)) {
                TagStudy tagStudies = tagStudyRepository.findByStudyAndTag(study, tag);
                study.deleteTagStudy(tagStudies);
                tagStudyRepository.delete(tagStudies);
            }
            else after.remove(tag); // 공통된 태그
        }

        // 추가할 태그
        for(Tag tag : after) {
            TagStudy tagStudy = new TagStudy();
            tagStudy.setStudy(study);
            tagStudy.setTag(tag);
        }

        return makeListTags(names);
    }

    // 태그 수정 By Study-Id
    public List<Tag> updateTag(long studyId, List<String> names) {
        // 스터디 조회
        Study study = studyRepository.findById(studyId).get();

        List<Tag> before = findTagsByStudyId(studyId);
        List<Tag> after = makeListTags(names);

        // before을 기준으로 정렬
        for(Tag tag : before) {
            // 삭제될 태그
            if(!after.contains(tag)) {
                TagStudy tagStudies = tagStudyRepository.findByStudyAndTag(study, tag);
                study.deleteTagStudy(tagStudies);
                tagStudyRepository.delete(tagStudies);
            }
            else after.remove(tag); // 공통된 태그
        }

        // 추가할 태그
        for(Tag tag : after) {
            TagStudy tagStudy = new TagStudy();
            tagStudy.setStudy(study);
            tagStudy.setTag(tag);
        }

        return makeListTags(names);
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

    // 태그 조회 by Name
    public Tag findTag(String name) {
        return verifiedTag(name);
    }

    // 태그 조회 by userId
    public List<Tag> findTagsByUserId(HttpServletRequest request) {
        UserEntity user = token.findByToken(request);
        Set<Tag> tags = new HashSet<>();
        user.getUserStudies().stream().forEach(
                userStudyEntity -> {
                    userStudyEntity.getStudy().getTagStudies().stream().forEach(
                            tagStudy -> {
                                tags.add(findTag(tagStudy.getTag().getTagId()));
                            }
                    );
                }
        );

        List<Tag> tagList = tags.stream()
                .map(tag -> tag)
                .collect(Collectors.toList());

        return tagList;
    }

    // 태그 조회 by studyId
    public List<Tag> findTagsByStudyId(long studyId) {
        return tagRepository.findByTagStudiesStudy(studyRepository.findById(studyId).get());
    }

    // 태그 조회 by study
    public List<Tag> findTagsByStudy(Study study) {
        return tagRepository.findByTagStudiesStudy(study);
    }

    // List<String>으로 List<Tag> 만들기
    public List<Tag> makeListTags(List<String> names) {
        List<Tag> tags = new ArrayList<>();
        for(String name : names) {
            tags.add(findTag(name));
        }

        return tags;
    }

    // 태그 전체 조회(페이지)
    public Page<Tag> findTags(int page, int size) {
        return tagRepository.findAll(PageRequest.of(page, size,
                Sort.by("tagId").descending()));
    }

    // 태그 전체 조회
    public List<Tag> findTags() {
        return tagRepository.findAll();
    }

    // 태그 증명
    public Tag verifiedTag(long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        Tag tag =
                optionalTag.orElseThrow(() ->
                        new BusinessLogicException((ExceptionCode.MESSAGE_NOT_FOUND)));

        return tag;
    }

    // 태그 증명 by tagName
    public Tag verifiedTag(String name) {
        Optional<Tag> optionalTag = tagRepository.findByName(name);
        Tag tag =
                optionalTag.orElseThrow(() ->
                        new BusinessLogicException((ExceptionCode.MESSAGE_NOT_FOUND)));

        return tag;
    }

    // 태그 초기 저장 함수
    public void saveTags() {
        for(String name : Init.tagList) {
            Tag tag = new Tag();
            tag.setName(name);
            tagRepository.save(tag);
        }
    }
}
