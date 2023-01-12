package codestates.main22.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MessageResponseDto {
    @AllArgsConstructor
    @Getter
    public static class Post {
        public String content;
        public LocalDateTime dateTime;
        public String userName;
    }
}
