package codestates.main22.tree.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class TreeDto {
    @Getter
    @AllArgsConstructor
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
    @AllArgsConstructor
    public static class Response {
        private long treeId;
        private int treePoint;
        private String treeImage;
    }
}
