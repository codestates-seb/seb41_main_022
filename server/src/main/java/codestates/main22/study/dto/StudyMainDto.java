package codestates.main22.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class StudyMainDto {
    @Getter
    @AllArgsConstructor
    public static class Patch {
        private String teamName;
        private String summary;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class HeaderResponse {
        private String teamName;
        private boolean onOff;
        private boolean openClose;
    }

    @Getter
    @AllArgsConstructor
    public static class MainResponse {
        private String teamName;
        private String summary;
        public List<String> tags;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class MainPatch {
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
    public static class AuthResponse {
        private boolean isMember;
        private boolean isHost;
        private boolean isRequest;
    }
}
