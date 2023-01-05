package codestates.main22.study.controller;

import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.tree.mapper.TreeMapper;
import codestates.main22.tree.service.TreeService;

public class StudyController {
    private final StudyService studyService;
    private final StudyMapper mapper;

    public StudyController(StudyService studyService,StudyMapper mapper) {
        this.studyService = studyService;
        this.mapper = mapper;
    }
}
