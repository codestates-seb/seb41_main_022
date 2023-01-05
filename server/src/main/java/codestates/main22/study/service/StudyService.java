package codestates.main22.study.service;

import codestates.main22.study.repository.StudyRepository;
import org.springframework.stereotype.Service;

@Service
public class StudyService {
    private final StudyRepository studyRepository;
    public StudyService(StudyRepository studyRepository) {this.studyRepository = studyRepository;}
}
