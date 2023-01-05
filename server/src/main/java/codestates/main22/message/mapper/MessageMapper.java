package codestates.main22.message.mapper;

import codestates.main22.message.dto.MessageReponseDto;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messagePostDtoToMessage(MessageRequestDto.Post post);
    MessageReponseDto.Post messageToMessagePostDto(Message message);
}