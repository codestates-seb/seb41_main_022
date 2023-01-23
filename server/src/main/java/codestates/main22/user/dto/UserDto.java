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
        private String token;
        private String refresh;
        private List<Long> studyId; //테스트용으로 가입 스터디 미리 넣어 봤음
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
        private List<Long> studyId;
    }
    // 이미지만 담아서 리스폰스
    @Getter
    @Setter
    @AllArgsConstructor
    public static class ImageResponse {
        private String imgUrl;
    }

    //이름만 수정
    @Getter
    @Setter
    @AllArgsConstructor
    public static class NamePatch {
        private long userId;
        @NotBlank(message = "닉네임을 입력해주세요.")
        private String username;
    }

    //이름만 담아서 리스폰스
    @Getter
    @Setter
    @AllArgsConstructor
    public static class NameResponse {
        private String username;
    }

    //유저 식별값, 유저 닉네임, 유저 이미지만 담아서 리스폰
    @Getter
    @Setter
    @AllArgsConstructor
    public static class SearchUserResponse {
        private long userId;
        private String username;
        private String imgUrl;
    }

    //스터디홀에서 유저 조회에 필요한 정보만 담아서 리스폰
    @Getter
    @Setter
    @AllArgsConstructor
    public static class StudyUserResponse {
        private String username;
        private String imgUrl;
        private String role;
    }
}