package codestates.main22.study.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.tag.entity.Tag;
import codestates.main22.tag.service.TagService;
import codestates.main22.tree.service.TreeService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.entity.UserStudyEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.user.repository.UserStudyRepository;
import codestates.main22.utils.Init;
import codestates.main22.utils.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
//@AllArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final TagService tagService;
    private final UserStudyRepository userStudyRepository;
    private final TreeService treeService;
    private final CustomAuthorityUtils customAuthorityUtils;
    private final Token token;


    public StudyService(StudyRepository studyRepository,
                        UserRepository userRepository,
                        TagService tagService,
                        UserStudyRepository userStudyRepository,
                        TreeService treeService,
                        CustomAuthorityUtils customAuthorityUtils,
                        Token token) {
        this.studyRepository = studyRepository;
        this.userRepository = userRepository;
        this.tagService = tagService;
        this.userStudyRepository = userStudyRepository;
        this.treeService = treeService;
        this.customAuthorityUtils = customAuthorityUtils;
        this.token = token;
    }

    @Transactional
    public Study createStudy(Study study, HttpServletRequest request) {
        // 토큰값으로 스터디장에게 권한 부여
        UserEntity user = token.findByToken(request);
        studyRepository.save(study);
        user.getRole().add(customAuthorityUtils.createStudyRoles(study.getStudyId(), true));

        // leaderId 등록
        study.setLeaderId(user.getUserId());

        // user와 연관관계 생성
        UserStudyEntity userStudyEntity = new UserStudyEntity();
        userStudyEntity.setUser(user);
        userStudyEntity.setStudy(study);

        // tree 생성하기
        treeService.createTree(study);

        // image 자동 세팅
        int randImage = randBetween(1,30);
        if(study.getImage() == null) {study.setImage(Init.S3Url + "main" + randImage + ".png");}

        return study;
    }

    public Study joinStudy(Study study, UserEntity user) {
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
        Optional.ofNullable(study.isOnOff()).ifPresent(findStudy::setOnOff);
        Optional.ofNullable(study.getContent()).ifPresent(findStudy::setContent);
        Optional.ofNullable(study.getNotice()).ifPresent(findStudy::setNotice);
        Optional.ofNullable(study.getImage()).ifPresent(findStudy::setImage);
        Optional.ofNullable(study.getLeaderId()).ifPresent(findStudy::setLeaderId);
        Optional.ofNullable(study.getUserStudies()).ifPresent(findStudy::setUserStudies);
        Optional.ofNullable(study.getTagStudies()).ifPresent(findStudy::setTagStudies);
        return studyRepository.save(study);
    }

    // 방장이 가입 수락 버튼을 눌렀을 때
    public Study giveUserAuth(Study study, long userId) {
        UserEntity user = userRepository.findById(userId).get();

        UserStudyEntity userStudyEntity = new UserStudyEntity();
        userStudyEntity.setUser(user);
        userStudyEntity.setStudy(study);

        user.getRole().add(customAuthorityUtils.createStudyRoles(study.getStudyId(), false));

        userStudyRepository.save(userStudyEntity);

        // 새로운 스터디원 가입 시 트리 점수 추가
        treeService.updateTreePoint(study, 30);

        return study;
    }

//    public Study removeUserAuth(Study study, long userId) {
//        UserEntity user = userRepository.findById(userId).get();
//        UserStudyEntity userStudyEntity = userStudyRepository.findByUserAndStudy(user, study);
//        user.removeStudy(study);
//        userStudyRepository.delete(userStudyEntity);
//        return study;
//    }

    // 스터디원의 스터디 탈퇴
    @Transactional
    public void removeUserAuth(Study study, long userId) {
        UserEntity user = userRepository.findByUserId(userId);

        // 스터디와 유저의 연관관계 제거
        UserStudyEntity userStudyEntity = userStudyRepository.findByUserAndStudy(user, study);
        study.getUserStudies().remove(userStudyEntity);
        user.getUserStudies().remove(userStudyEntity);
        userStudyRepository.delete(userStudyEntity);

        // user의 스터디원 권한 삭제
        user.getRole().remove(customAuthorityUtils.createStudyRoles(study.getStudyId(), false));

        // 스터디 탈퇴 시 트리 포인트 감소
        treeService.updateTreePoint(study, -30);
    }

    public Study findStudy(long studyId) {
        Study study = VerifiedStudy(studyId);
        return study;
    }

    public Page<Study> findStudies(int page, int size) {
        return studyRepository.findAll(PageRequest.of(page, size, Sort.by("studyId").descending()));
    }

    // 달이 바뀌었을 때, 모든 스터디에 새로운 트리 추가하기
    public void createTreesAllStudies() {
        studyRepository.findAll().stream()
                .forEach(study -> {
                    // 트리가 생성된 달과 현재 달이 다른 경우에 새로운 트리 추가
                    if(treeService.findfinalCreatedTree(study).getMakeMonth() != LocalDate.now().getMonthValue())
                        treeService.createTree(study);
                });
    }

    // 스터디 필터링 - 처음 조회
    public Page<Study> findStudiesByFilters(
            int page,
            int size
    ) {
        createTreesAllStudies(); // 달이 바뀌었을 때, 새로운 트리 추가하기

        List<Study> studies = studyRepository.findAll().stream()
                .filter(study -> study.isOpenClose()) // 공개된 스터디 필터링
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
        // 필요한 스터디 조회
        Set<Study> studies = new HashSet<>();
        if(tagString.size() == 0) { // 태그 값들이 전부 없는 경우 -> 모든 스터디 조회
            studies = studyRepository.findAll().stream()
                    .collect(Collectors.toSet());
        } else { // 태그로 스터디 조회
            List<Tag> tags = tagService.makeListTags(tagString); // 필요한 태그로 변환
            for(Tag tag : tags) {
                addStudyInSet(studies, studyRepository.findByTagStudiesTag(tag));
            }
        }

        // 공개된 스터디 필터링 & search로 스터디 조회
        studies = studies.stream()
                .filter(study -> study.isOpenClose()) // 공개된 스터디 필터링
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

        Optional.ofNullable(changedStudy.getTeamName()).ifPresent(study::setTeamName);
        Optional.ofNullable(changedStudy.getSummary()).ifPresent(study::setSummary);
        Optional.ofNullable(changedStudy.getDayOfWeek()).ifPresent(study::setDayOfWeek);
        Optional.ofNullable(changedStudy.getWant()).ifPresent(study::setWant);
        Optional.ofNullable(changedStudy.getStartDate()).ifPresent(study::setStartDate);
        Optional.ofNullable(changedStudy.isOnOff()).ifPresent(study::setOnOff);
        Optional.ofNullable(changedStudy.isOpenClose()).ifPresent(study::setOpenClose);
        Optional.ofNullable(changedStudy.getContent()).ifPresent(study::setContent);
        Optional.ofNullable(changedStudy.getImage()).ifPresent(study::setImage);

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
            UserEntity user = token.findByToken(request);
            List<UserStudyEntity> userStudies = userStudyRepository.findByUser(user);
            List<Study> studies = userStudies.stream().map(UserStudyEntity::getStudy).collect(Collectors.toList());
        return studies;
    }

    // Tag 생성
    public List<String> createTagStudies(Study study, List<String> tags) {
        tagService.createTagStudies(study, tags);

        return tags;
    }

    // Tag 수정
    public List<String> updateTag(Study study, List<String> tags) {
        tagService.updateTag(study.getStudyId(), tags);

        return tags;
    }

    // 태그 조회
    public List<String> findTagsByStudy(Study study) {
        List<Tag> tags = tagService.findTagsByStudy(study);
        return tags.stream()
                .map(tag -> tag.getName())
                .collect(Collectors.toList());
    }

    //랜덤 이미지를 넣기 위한 로직
    private static final Random rng = new Random();
    public static int randBetween(int min, int max) {return min+rng.nextInt(max-min+1);}

    public List<Study> findStudiesByUserNoToken(long userId) {
        UserEntity user = verifiedUser(userId);
        List<UserStudyEntity> userStudies = userStudyRepository.findByUser(user);
        List<Study> studies = userStudies.stream().map(UserStudyEntity::getStudy).collect(Collectors.toList());
        return studies;
    }

    public UserEntity verifiedUser(long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }

}