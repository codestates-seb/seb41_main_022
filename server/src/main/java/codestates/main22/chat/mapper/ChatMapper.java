package codestates.main22.chat.mapper;

import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.ChatEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatEntity chatPostDtoToChatEntity(ChatDto.Post post);
    ChatEntity chatPatchDtoToChatEntity(ChatDto.Patch patch);
    ChatDto.Response chatEntityToResponseCheck(ChatEntity chat);
    List<ChatDto.Response> chatsToResponse(List<ChatEntity> chats);
}
