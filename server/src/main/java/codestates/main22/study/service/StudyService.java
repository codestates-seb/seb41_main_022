package codestates.main22.study.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import codestates.main22.tag.entity.TagStudy;
import codestates.main22.tree.entity.Tree;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.entity.UserStudyEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.user.repository.UserStudyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final UserStudyRepository userStudyRepository;
    private final CustomAuthorityUtils customAuthorityUtils;

    public StudyService(StudyRepository studyRepository,
                        UserRepository userRepository,
                        CustomAuthorityUtils customAuthorityUtils,
                        UserStudyRepository userStudyRepository) {
        this.studyRepository = studyRepository;
        this.userRepository = userRepository;
        this.userStudyRepository = userStudyRepository;
        this.customAuthorityUtils = customAuthorityUtils;
    }

    @Transactional
    public Study createStudy(Study study, HttpServletRequest request) {
        studyRepository.save(study);
        
        // 토큰값으로 스터디장에게 권한 부여
        UserEntity user = userRepository.findByToken(request);
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

    public Study findStudy(long studyId) {
        Study study = VerifiedStudy(studyId);
        return study;
    }

    public Page<Study> findStudies(int page, int size) {
        return studyRepository.findAll(PageRequest.of(page, size, Sort.by("studyId").descending()));
    }

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

//    public List<Study> findStudiesByUser(long userId) {
//        List<Study> studies = studyRepository.findAll();
//        return studies.stream()
//                .filter(study -> isUserInStudy(userId, study.getStudyId()))
//                .collect(Collectors.toList());
//    }
//
//    public boolean isUserInStudy(long userId, long studyId) {
//        Study findStudy = findStudy(studyId);
//        List<UserStudyEntity> userStudies = findStudy.getUserStudies();
//        return userStudies.stream()
//                .anyMatch(userStudy -> userStudy.getUser().getUserId() == userId);
//    }


}
