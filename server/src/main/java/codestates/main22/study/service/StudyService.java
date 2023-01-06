package codestates.main22.study.service;

import codestates.main22.study.entity.Study;
import codestates.main22.study.repository.StudyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    public StudyService(StudyRepository studyRepository) {this.studyRepository = studyRepository;}

    public Study createStudy(Study study) {
        boolean[] week = {false, false, false, false, false, false, false};
        if (study.getDayOfWeek() == null) {
            study.setDayOfWeek(week);
        }
        return studyRepository.save(study);
    }
}
