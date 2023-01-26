package codestates.main22.chat.mapper;

import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.Chat;
import codestates.main22.user.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    Chat chatPostDtoToChat(ChatDto.Post post);
    Chat chatPatchDtoToChat(ChatDto.Patch patch);
    ChatDto.Response chatToResponseCheck(Chat chat, UserEntity user);
//    List<ChatDto.Response> chatsToResponse(List<Chat> chats);
    default List<ChatDto.Response> chatsToResponse(List<Chat> chats, Map<Long, UserEntity> users) {
        List<ChatDto.Response> responses = new ArrayList<>();

        for(Chat chat : chats) {
            List<AnswerDto.Response> list = new ArrayList<>();
            chat.getAnswers().stream().forEach(answer -> {
                AnswerDto.Response answerResponse = new AnswerDto.Response(
                        answer.getAnswerId(),
                        answer.getAnswerUserId(),
                        users.get(answer.getAnswerUserId()).getUsername(),
                        users.get(answer.getAnswerUserId()).getImgUrl(),
                        answer.getContent(),
                        answer.getCreatedAt()
                );
                list.add(answerResponse);
            });

            UserEntity user = users.get(chat.getChatUserId());
            ChatDto.Response response = new ChatDto.Response(
                    chat.getChatId(),
                    chat.getChatUserId(),
                    user.getUsername(),
                    user.getImgUrl(),
                    chat.getContent(),
                    chat.getIsClosedChat(),
                    chat.getCreatedAt(),
                    list
            );
            responses.add(response);
        }

        return responses;
    }
}
