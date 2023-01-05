package codestates.main22.message.controller;

import codestates.main22.message.dto.MessageReponseDto;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.entity.Message;
import codestates.main22.message.mapper.MessageMapper;
import codestates.main22.message.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/message")
@Valid
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageController(MessageService messageService, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
    }

    @PostMapping
    public ResponseEntity postMessage(@Valid @RequestBody MessageRequestDto.Post post) {
        Message Message = messageService.createMessage(messageMapper.messagePostDtoToMessage(post));
        MessageReponseDto.Post response = messageMapper.messageToMessagePostDto(Message);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}