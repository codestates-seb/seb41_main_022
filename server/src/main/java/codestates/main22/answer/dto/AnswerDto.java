package codestates.main22.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class AnswerDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        //private long answerUserId; // 이 부분이 필요한지는 고민해보기

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        private Boolean isClosedChat; // 단일로만 사용했을때 postman 오류가 나서 임시로 넣음
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        private Boolean isClosedChat; // 단일로만 사용했을때 postman 오류가 나서 임시로 넣음
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long answerId;
        private long answerUserId;
        private String content;

        private Boolean isClosedChat; // 단일로만 사용했을때 postman 오류가 나서 임시로 넣음
    }
}
