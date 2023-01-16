package codestates.main22.study.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.service.TagService;
import codestates.main22.tree.entity.Tree;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.entity.UserStudyEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.user.repository.UserStudyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final UserStudyRepository userStudyRepository;
    private final CustomAuthorityUtils customAuthorityUtils;


    public StudyService(StudyRepository studyRepository,
                        UserRepository userRepository,
                        TagService tagService,
                        CustomAuthorityUtils customAuthorityUtils,
                        UserStudyRepository userStudyRepository) {
        this.studyRepository = studyRepository;
        this.userRepository = userRepository;
        this.tagService = tagService;
        this.userStudyRepository = userStudyRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Transactional
    public Study createStudy(Study study, HttpServletRequest request) {
        // 토큰값으로 스터디장에게 권한 부여
        UserEntity user = userRepository.findByToken(request);
        studyRepository.save(study);
        user.getRole().add(customAuthorityUtils.createStudyRoles(study.getStudyId(), true));

        // leaderId 등록
        study.setLeaderId(user.getUserId());

        // user와 연관관계 생성
        UserStudyEntity userStudyEntity = new UserStudyEntity();
        userStudyEntity.setUser(user);
        userStudyEntity.setStudy(study);

        // tree 생성하기
        Tree tree = new Tree();
        tree.setTreeImage("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FpjywQ%2FbtrVZVh0ers%2Fe9JHXVkvDOnVrkcf8pvpik%2Fimg.png");
        tree.setMakeMonth(LocalDate.now().getMonthValue());
        tree.setStudy(study);

        return study;
    }

    public Study joinStudy(Study study, UserEntity user) {  // 방장이 가입 수락 버튼을 눌렀을 때
        // user 와 연관관계 생성
        UserStudyEntity userStudyEntity = new UserStudyEntity();
        userStudyEntity.setUser(user);
        userStudyEntity.setStudy(study);

        studyRepository.save(study);
        return study;
    }

    public Study updateStudy(Study study) {
        Study findStudy = VerifiedStudy(study.getStudyId());
        Optional.ofNullable(study.getTeamName()).ifPresent(findStudy::setTeamName);
        Optional.ofNullable(study.getSummary()).ifPresent(findStudy::setSummary);
        Optional.ofNullable(study.getDayOfWeek()).ifPresent(findStudy::setDayOfWeek);
        Optional.ofNullable(study.getWant()).ifPresent(findStudy::setWant);
        Optional.ofNullable(study.getStartDate()).ifPresent(findStudy::setStartDate);
        Optional.ofNullable(study.isProcedure()).ifPresent(findStudy::setProcedure);
        Optional.ofNullable(study.getContent()).ifPresent(findStudy::setContent);
        Optional.ofNullable(study.getNotice()).ifPresent(findStudy::setNotice);
        Optional.ofNullable(study.getImage()).ifPresent(findStudy::setImage);
        Optional.ofNullable(study.getLeaderId()).ifPresent(findStudy::setLeaderId);
        Optional.ofNullable(study.getUserStudies()).ifPresent(findStudy::setUserStudies);
        Optional.ofNullable(study.getTagStudies()).ifPresent(findStudy::setTagStudies);
        return studyRepository.save(study);
    }

    public Study giveUserAuth(Study study, long userId) {
        UserEntity user = userRepository.findById(userId).get();

        UserStudyEntity userStudyEntity = new UserStudyEntity();
        userStudyEntity.setUser(user);
        userStudyEntity.setStudy(study);

        userStudyRepository.save(userStudyEntity);
        return study;
    }

//    public Study removeUserAuth(Study study, long userId) {
//        UserEntity user = userRepository.findById(userId).get();
//        UserStudyEntity userStudyEntity = userStudyRepository.findByUserAndStudy(user, study);
//        user.removeStudy(study);
//        userStudyRepository.delete(userStudyEntity);
//        return study;
//    }

    @Transactional
    public void removeUserAuth(Study study, long userId) {
        UserEntity user = userRepository.findByUserId(userId);
        UserStudyEntity userStudyEntity = userStudyRepository.findByUserAndStudy(user, study);
        study.getUserStudies().remove(userStudyEntity);
        user.getUserStudies().remove(userStudyEntity);
        userStudyRepository.delete(userStudyEntity);
    }

    public Study findStudy(long studyId) {
        Study study = VerifiedStudy(studyId);
        return study;
    }

    public Page<Study> findStudies(int page, int size) {
        return studyRepository.findAll(PageRequest.of(page, size, Sort.by("studyId").descending()));
    }

    // 스터디 필터링 - 처음 조회
    public Page<Study> findStudiesByFilters(
            int page,
            int size
    ) {
        List<Study> studies = studyRepository.findAll().stream()
                .filter(study -> !study.isOpenClose()) // 공개된 스터디 필터링
                .sorted(Comparator.comparing(Study::getCreatedAt).reversed()) // new(최신) 순으로 정렬(default)
                .collect(Collectors.toList());

        // 페이지네이션
        int start = page * size;
        int end = Math.min(start + size, studies.size());
        return new PageImpl<>(studies.subList(start, end), PageRequest.of(page, size), studies.size());
    }

    // 스터디 필터링
    public Page<Study> findStudiesByFilters(
            int page,
            int size,
            String search,
            String filter,
            List<String> tagString
    ) {
        // 태그로 스터디 조회
        List<Tag> tags = tagService.makeListTags(tagString); // 필요한 태그로 변환
        Set<Study> studies = new HashSet<>();
        for(Tag tag : tags) {
            addStudyInSet(studies, studyRepository.findByTagStudiesTag(tag));
        }

        // 공개된 스터디 필터링 & search로 스터디 조회
        studies = studies.stream()
                .filter(study -> !study.isOpenClose()) // 공개된 스터디 필터링
                .filter(study -> searchWord(study, search)) // search로 스터디 조회
                .collect(Collectors.toSet());

        // filter로 정렬하기
        List<Study> listStudies = studies.stream().collect(Collectors.toList()); // set 때문에 자동 random 순으로 정렬되어 있음
        if(filter.equals("name")) { // name(이름) 순으로 정렬
            listStudies = listStudies.stream()
                    .sorted(Comparator.comparing(Study::getCreatedAt).reversed())
                    .sorted(Comparator.comparing(Study::getTeamName))
                    .collect(Collectors.toList());
        }
        else if(!filter.equals("random")) { // new(최신) 순으로 정렬(default)
            listStudies = listStudies.stream()
                    .sorted(Comparator.comparing(Study::getCreatedAt).reversed())
                    .collect(Collectors.toList());
        }

        // 페이지네이션
        int start = page * size;
        int end = Math.min(start + size, listStudies.size());
        return new PageImpl<>(listStudies.subList(start, end), PageRequest.of(page, size), listStudies.size());
    }

    // Set<Study>에 List<Study> 내용 추가힉
    public Set<Study> addStudyInSet(Set<Study> setStudies, List<Study> listStudies) {
        listStudies.forEach(listStudy -> {
            setStudies.add(listStudy);
        });

        return setStudies;
    }

    // 검색어가 있는지 탐색 (word == seach)
    public boolean searchWord(Study study, String word) {
        // 검색값이 없을 경우
        if(word == "") return true;
        // teamName에서 탐색
        if (study.getTeamName().contains(word)) return true;
        // summary에서 탐색
        if (study.getSummary().contains(word)) return true;

        return false;
    }

//    public List<Study> findStudiesByTagId(long tagId) {
//        return studyRepository.findByTagStudiesTag(tagService.findTag(tagId));
//    }

    public void deleteStudy(long studyId) {studyRepository.deleteById(studyId);}

    public Study VerifiedStudy(long studyId) {
        Optional<Study> optionalStudy = studyRepository.findById(studyId);
        Study findStudy = optionalStudy.orElseThrow(() -> new BusinessLogicException(ExceptionCode.STUDY_NOT_FOUND));
        return findStudy;
    }

    public Study updateStudyNotice(long studyId, Study changedStudy) {
        Study study = VerifiedStudy(studyId);
//        Optional.ofNullable(study.getDayOfWeek()).ifPresent(findStudy::setDayOfWeek);
//        Optional.ofNullable(study.getNotice()).ifPresent(findStudy::setNotice);
        study.setNotice(changedStudy.getNotice());
//        study.setDayOfWeek(changedStudy.getDayOfWeek());
        return studyRepository.save(study);
    }

    public Study updateMainBody(long studyId, Study changedStudy) {
        Study study = VerifiedStudy(studyId);
        study.setTeamName(changedStudy.getTeamName());
        study.setSummary(changedStudy.getSummary());
        study.setContent(changedStudy.getContent());
        return studyRepository.save(study);
    }

    public boolean isMember(long userId, long studyId){
        Study findStudy = findStudy(studyId);
        List<UserStudyEntity> userStudies = findStudy.getUserStudies();
        return userStudies.stream().anyMatch(userStudy -> userStudy.getUser().getUserId() == userId);
    }

    public void addRequester(Study study, Long userId) {
        study.getRequester().add(userId);
        studyRepository.save(study);
    }

    public void removeRequester(Study study, Long userId) {
        study.getRequester().remove(userId);
        studyRepository.save(study);
    }

//    public List<Study> findStudiesByUser(HttpServletRequest request) {
//        UserEntity user = userRepository.findByToken(request);
//        List<Study> studies = studyRepository.findByUserStudiesUser(user);
//
//        return studies;
//    }
    public List<Study> findStudiesByUser(HttpServletRequest request) {
            UserEntity user = userRepository.findByToken(request);
            List<UserStudyEntity> userStudies = userStudyRepository.findByUser(user);
            List<Study> studies = userStudies.stream().map(UserStudyEntity::getStudy).collect(Collectors.toList());
        return studies;
    }


}
