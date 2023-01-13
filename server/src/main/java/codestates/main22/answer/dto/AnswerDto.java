package codestates.main22.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class AnswerDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank(message = "내용을 입력해주세요.")
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long answerId;
        private String username;
        private String imgUrl;
        private String content;
        private LocalDateTime AnswerCreatedAt;
    }
}
