package codestates.main22.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class TagRequestDto {
    @Getter
    public static class Post{
        public String name;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        public long tagId;
        public String name;
    }
}