package codestates.main22.study.service;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    public StudyService(StudyRepository studyRepository) {this.studyRepository = studyRepository;}

    public Study createStudy(Study study) {
        return studyRepository.save(study);
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
}
