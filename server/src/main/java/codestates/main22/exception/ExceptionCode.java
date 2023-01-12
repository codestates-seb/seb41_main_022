package codestates.main22.exception;

import lombok.Getter;

public enum ExceptionCode {
    STUDY_NOT_FOUND(404, "Study not found"),
    TREE_NOT_FOUND(404, "Tree not found"),
    USER_NOT_FOUND(404, "User not found"),
    CALENDAR_NOT_FOUND(404, "Calendar not found"),
    MESSAGE_NOT_FOUND(404, "Message not found"),
    TAG_NOT_FOUND(404, "Tag not found"),
    CHAT_NOT_FOUND(404, "Chat not found"),
    ANSWER_NOT_FOUND(404, "Answer not found"),
    UNREGISTERED_USER(500, "You are an unregistered user"),
    NOT_AN_ADMINISTRATOR(500, "you are not an administrator"),    
    UNAUTHORIZED_MEMBER(404, "Unauthorized member"),
    NO_AUTHORITY(404, "No Authority. Only Access Study leader");


    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
