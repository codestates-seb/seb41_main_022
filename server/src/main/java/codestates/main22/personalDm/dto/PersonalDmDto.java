package codestates.main22.personalDm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class PersonalDmDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private long opponentUserId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long personalDmId;
        private long opponentUserId;
    }
}
