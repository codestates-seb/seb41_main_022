package codestates.main22.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TagRequestDto {
    @AllArgsConstructor
    @Getter
    public static class Post{
        public long tagId;
        public String name;
    }
}