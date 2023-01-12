package codestates.main22.tree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


public class TreeDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Post {
        private String treeImage;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch {
        private long treeId;
        private int treePoint;
        private String treeImage;

        public void setTreeId(long treeId) {this.treeId = treeId;}
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ListResonse<T> {
        private List<T> trees;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserResponse {
        private long treeId;
        private int treePoint;
        private String treeImage;
        private LocalDateTime createdAt;
        private int makeMonth;
        private String teamName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class StudyResponse {
        private long treeId;
        private int treePoint;
        private String treeImage;
        private LocalDateTime createdAt;
        private int makeMonth;
    }
}
