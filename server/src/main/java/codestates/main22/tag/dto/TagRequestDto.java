package codestates.main22.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class TagRequestDto {
    @Getter
    public static class Post{
        public List<String> tags;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Patch{
        public long tagId;
        public String name;
    }
}