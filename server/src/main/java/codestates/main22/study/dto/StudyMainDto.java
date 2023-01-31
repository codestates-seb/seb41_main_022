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
        // private String status; // TODO enum 형태 (비가입, 신청중, 가입) 신청 버튼 이후 돌아올 것
        // private boolean isHost; // TODO 방장인지 아닌지 판별 - 로그인이 구현된 이후 돌아올 것
        private String summary;
        public List<String> tags;
        private String content;
    }

    @Getter
    @AllArgsConstructor
    public static class MainPatch {
        // private String status; // TODO enum 형태 (비가입, 신청중, 가입) 신청 버튼 이후 돌아올 것
        // private boolean isHost; // TODO 방장인지 아닌지 판별 - 로그인이 구현된 이후 돌아올 것
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
