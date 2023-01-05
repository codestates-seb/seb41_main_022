package codestates.main22.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.List;

public class StudyDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private String title;
        private boolean[] dayOfWeek;
        private int want;
        private String startDate;
        private boolean procedure;
        private boolean openClose;
        private String content;
        private String image;
        @ElementCollection
        private List<String> tags;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long studyId;
        private String title;
        private boolean[] dayOfWeek;
        private int want;
        private String startDate;
        private boolean procedure;
        private boolean openClose;
        private String contact;
        private String content;
        private String notice;
        private String image;
        @ElementCollection
        private List<String> tags;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long studyId;
        private String title;
        private boolean[] dayOfWeek;
        private int want;
        private String startDate;
        private boolean procedure;
        private boolean openClose;
        private String contact;
        private String content;
        private String notice;
        private String image;
        @ElementCollection
        private List<String> tags;
    }
}