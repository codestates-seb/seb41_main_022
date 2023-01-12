package codestates.main22.message.mapper;

import codestates.main22.message.dto.MessageResponseDto;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.entity.Message;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageReqPostDtoToMessage(MessageRequestDto.Post post);
    Message messageReqPatchDtoToMessage(MessageRequestDto.Patch patch);
    MessageResponseDto.Post messageToMessageResPostDto(Message message);
//    default List<MessageResponseDto.UserResponse> messagesToMessageResPostDtos(List<Object> messagesAndUser) {
//        List<MessageResponseDto.UserResponse> responses = new ArrayList<>();
//    };

    List<MessageResponseDto.Post> messagesToMessageResPostDtos(List<Message> messages);
}