package codestates.main22.study.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;

public class StudyDto {
    @Getter
    @AllArgsConstructor
    public static class Post {}

    public static class Patch {}

    public static class Response {}
}

