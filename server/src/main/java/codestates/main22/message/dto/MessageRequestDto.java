package codestates.main22.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MessageRequestDto {
    @AllArgsConstructor
    @Setter
    @Getter
    public static class Post{
        public String content;
        public LocalDateTime dateTime;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        public long messageId;
        public String content;
        public LocalDateTime dateTime;
    }
}