package codestates.main22.message.mapper;

import codestates.main22.answer.dto.AnswerDto;
import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.Chat;
import codestates.main22.message.dto.MessageResponseDto;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.entity.Message;
import codestates.main22.user.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageReqPostDtoToMessage(MessageRequestDto.Post post);
    Message messageReqPatchDtoToMessage(MessageRequestDto.Patch patch);
    MessageResponseDto.UserResponse messageToMessageUserResponseDto(Message message, UserEntity user);

//    default List<MessageResponseDto.UserResponse> messagesToMessageResPostDtos(List<Object> messagesAndUser) {
//        List<MessageResponseDto.UserResponse> responses = new ArrayList<>();
//    };

    default List<MessageResponseDto.UserResponse> messagesToMessageUserResponse(List<Message> messages, Map<Long, UserEntity> users) {
        List<MessageResponseDto.UserResponse> responses = new ArrayList<>();

        for(Message message : messages) {
            UserEntity user = users.get(message.getMessageUserId());
            MessageResponseDto.UserResponse response = new MessageResponseDto.UserResponse(
                    message.getContent(),
                    message.getDateTime(),
                    user.getUserId(),
                    user.getUsername(),
                    user.getImgUrl()
            );
            responses.add(response);
    }
        return responses;
    }
}