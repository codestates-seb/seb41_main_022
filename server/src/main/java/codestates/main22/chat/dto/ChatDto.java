package codestates.main22.chat.dto;

import codestates.main22.answer.dto.AnswerDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ChatDto {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        //private long chatUserId; // 이 부분이 필요한지는 고민해보기

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        private Boolean isClosedChat;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch {
        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        private Boolean isClosedChat;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long chatId;
        private long chatUserId;
        private String content;
        private Boolean isClosedChat; // 공개 = false, 비공개 = true

        private List<AnswerDto.Response> answers;
    }
}
