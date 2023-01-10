package codestates.main22.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TagResponseDto {
    @AllArgsConstructor
    @Getter
    public static class Post {
        public long tagId;
        public String name;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    public static class Get {
        public List<String> tags;
    }
}
