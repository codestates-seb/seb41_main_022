package codestates.main22.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String username;

        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        private String info;

        private String imgUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String username;

        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;

        private String info;

        private String imgUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long userId;
        private String username;
        private String email;
        private String info;
        private List<String> role; // 권한의 List 형태가 List<String> 형태가 맞는지?
        private String imgUrl;
        private String token; // 토큰??

    }
}