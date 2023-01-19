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
    UNREGISTERED_USER(404.5, "You are an unregistered user"),
    NOT_AN_ADMINISTRATOR(404.5, "you are not an study administrator"),
    UNAUTHORIZED_MEMBER(404.5, "Unauthorized member"),
    NO_AUTHORITY(404.5, "No Authority. Only Access Study leader"),
    NOT_JOIN_STUDY(404.5, "Not join study"),
    NOT_SAME(404, "UserId and UserName are not same");

    @Getter
    private double status;

    @Getter
    private String message;

    ExceptionCode(double code, String message) {
        this.status = code;
        this.message = message;
    }
}
