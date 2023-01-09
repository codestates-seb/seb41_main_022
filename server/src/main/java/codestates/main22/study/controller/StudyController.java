package codestates.main22.study.controller;

import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.study.dto.StudyDto;
import codestates.main22.study.entity.Study;
import codestates.main22.study.mapper.StudyMapper;
import codestates.main22.study.service.StudyService;
import codestates.main22.tree.dto.TreeDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/study")
@Validated
public class StudyController {
    private final StudyService studyService;
    private final StudyMapper studyMapper;

    public StudyController(StudyService studyService,StudyMapper mapper) {
        this.studyService = studyService;
        this.studyMapper = mapper;
    }

    @PostMapping
    public ResponseEntity postStudy(@Valid @RequestBody StudyDto.Post requestBody) {
        Study study = studyMapper.studyPostDtoToStudy(requestBody);
        Study createStudy = studyService.createStudy(study);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(createStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }

    @PatchMapping("/{study-id}")
    public ResponseEntity patchStudy(@PathVariable("study-id") @Positive long studyId,
                                     @Valid @RequestBody StudyDto.Patch requestBody) {
        requestBody.setStudyId(studyId);
        Study study = studyMapper.studyPatchDtoToStudy(requestBody);
        Study updateStudy = studyService.updateStudy(study);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(updateStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping("/{study-id}")
    public ResponseEntity getStudy(@PathVariable("study-id") @Positive long studyId) {
        Study findStudy = studyService.findStudy(studyId);
        StudyDto.Response response = studyMapper.studyToStudyResponseDto(findStudy);
        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getStudies(@RequestParam int page,
                                     @RequestParam int size) {
        Page<Study> pageStudies = studyService.findStudies(page-1, size);
        List<Study> studies = pageStudies.getContent();
        return new ResponseEntity<>(
                new MultiResponseDto<>(studyMapper.studiesToStudyResponseDto(studies), pageStudies), HttpStatus.OK);
    }

    @DeleteMapping("/{study-id}")
    public ResponseEntity deleteStudy(@PathVariable("study-id") @Positive long studyId) {
        studyService.deleteStudy(studyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
