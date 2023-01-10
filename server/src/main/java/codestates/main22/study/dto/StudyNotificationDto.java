package codestates.main22.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class StudyNotificationDto {
    @Getter
    @AllArgsConstructor
    public static class Patch {
        private List<String> dayOfWeek;
        private String notice;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private String notice;
    }

    @Getter
    @AllArgsConstructor
    public static class NoticeResponse {
        private List<String> dayOfWeek;
        private String notice;
    }
}
