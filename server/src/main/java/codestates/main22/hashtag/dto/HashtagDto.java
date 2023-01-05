package codestates.main22.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class HashtagDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "태그 이름을 입력해주세요.")
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long hashTagId;
        private String name;
    }
}