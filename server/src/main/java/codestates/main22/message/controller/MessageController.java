package codestates.main22.message.controller;

import codestates.main22.message.dto.MessageResponseDto;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.entity.Message;
import codestates.main22.message.mapper.MessageMapper;
import codestates.main22.message.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

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
        Message message = messageService.createMessage(messageMapper.messageReqPostDtoToMessage(post));
        MessageResponseDto.Post response = messageMapper.messageToMessageResPostDto(message);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{message-id}")
    public ResponseEntity getMessage(@PathVariable("message-id") @Positive long messageId) {
        Message message = messageService.findMessage(messageId);
        MessageResponseDto.Post response = messageMapper.messageToMessageResPostDto(message);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getMessages() {
        List<Message> messages = messageService.findMessages();
        List<MessageResponseDto.Post> responses = messageMapper.messagesToMessageResPostDtos(messages);

        return new ResponseEntity<>(responses, HttpStatus.CREATED);
    }

    @PatchMapping("/{message-id}")
    public ResponseEntity patchMessage(@Positive @PathVariable("message-id") long messageId,
            @Valid @RequestBody MessageRequestDto.Patch patch) {
        patch.setMessageId(messageId);
        Message message = messageService.updateMessage(messageMapper.messageReqPatchDtoToMessage(patch));
        MessageResponseDto.Post response = messageMapper.messageToMessageResPostDto(message);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @DeleteMapping("/{message-id}")
    public ResponseEntity<String> responseEntity(@Positive @PathVariable("message-id") long messageId) {
        messageService.deleteMessage(messageId);

        return new ResponseEntity<String>("Success delete message", HttpStatus.NO_CONTENT);
    }
}