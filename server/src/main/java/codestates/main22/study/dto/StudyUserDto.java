package codestates.main22.study.dto;

import codestates.main22.study.entity.Study;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class StudyUserDto {
    @Getter
    @AllArgsConstructor
    public static class Studys {
        private long studyId;
        private String teamName;
        private String summary;
        private String image;
    }

    @Getter
    @AllArgsConstructor
    public static class Response<T> {
        private long studyCount;
        private List<T> studies;
    }
}