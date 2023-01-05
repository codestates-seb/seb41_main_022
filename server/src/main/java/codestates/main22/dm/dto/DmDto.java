package codestates.main22.dm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class DmDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        @NotBlank
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long dmId;
        private String content;
        private LocalDateTime dateTime;
    }
}
