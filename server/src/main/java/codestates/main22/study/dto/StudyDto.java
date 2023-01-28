package codestates.main22.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class StudyDto {
    @Getter
    @AllArgsConstructor
    public static class Post {
        private String teamName;
        private String summary;
        public List<String> tags;
        private List<String> dayOfWeek;
        private int want;
        private LocalDate startDate;
        private boolean onOff;
        private boolean openClose;
        private String content;
        private String image;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private Long studyId;
        private String teamName;
        private String summary;
        private List<String> dayOfWeek;
        private int want;
        private LocalDate startDate;
        private boolean onOff;
        private boolean openClose;
        private String content;
        private String notice;
        private String image;
        //private long leaderId;

        public void setStudyId(long studyId) {this.studyId = studyId;}
    }

    @Getter
    @AllArgsConstructor
    public static class CardResponse {
        private Long studyId;
        private String teamName;
        private String summary;
        private List<String> dayOfWeek;
        private boolean onOff;
        private String image;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long studyId;
        private String teamName;
        private String summary;
        private List<String> dayOfWeek;
        private int want;
        private LocalDate startDate;
        private boolean onOff;
        private boolean openClose;
        private String content;
        private String notice;
        private String image;
        private long leaderId;
        private List<Long> requester;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ResponseTag {
        private Long studyId;
        private String teamName;
        private String summary;
        public List<String> tags;
        private List<String> dayOfWeek;
        private int want;
        private LocalDate startDate;
        private boolean onOff;
        private boolean openClose;
        private String content;
        private String notice;
        private String image;
        private long leaderId;
        private List<Long> requester;
    }

    @Getter
    @AllArgsConstructor
    public static class RequesterResponse {
        private List<Long> requester;
    }
}