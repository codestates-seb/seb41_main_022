package codestates.main22.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class StudyRequesterDto {
    @Getter
    @AllArgsConstructor
    public static class Post{

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private List<Long> requester;
    }
}
