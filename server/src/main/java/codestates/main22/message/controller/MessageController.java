package codestates.main22.message.controller;

import codestates.main22.dto.ListResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.message.dto.MessageResponseDto;
import codestates.main22.message.dto.MessageRequestDto;
import codestates.main22.message.entity.Message;
import codestates.main22.message.mapper.MessageMapper;
import codestates.main22.message.service.MessageService;
import codestates.main22.user.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity postMessage(@Valid @RequestBody MessageRequestDto.Post post,
                                      @Positive @RequestParam long studyId,
                                      HttpServletRequest request) {
        UserEntity user = messageService.findUserByToken(request);
        Message message = messageService.createMessage(studyId, messageMapper.messageReqPostDtoToMessage(post), request);
        MessageResponseDto.UserResponse response = messageMapper.messageToMessageUserResponseDto(message, user);

        return new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.CREATED
        );
    }

    @GetMapping // 해당 스터디 메세지 모아서 보기
    public ResponseEntity getMessages(@Positive @RequestParam long studyId,
                                      HttpServletRequest request) {
        List<Message> messages = messageService.findByStudyMessage(studyId);
        Map<Long, UserEntity> users = messageService.findUsers(messages);
        return new ResponseEntity<>(new ListResponseDto<>(messageMapper.messagesToMessageUserResponse(messages, users)), HttpStatus.OK);
    }

//    @GetMapping("/{message-id}")
//    public ResponseEntity getMessage(@PathVariable("message-id") @Positive long messageId) {
//        Message message = messageService.findMessage(messageId);
//        MessageResponseDto.Post response = messageMapper.messageToMessageResPostDto(message);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(response), HttpStatus.OK
//        );
//    }

//    @GetMapping // 모든 채팅 불러오는 기능이 불필요해서 주석 처리 (기본 URL사용하기 위함)
//    public ResponseEntity getMessages(@Positive @RequestParam int page,
//                                      @Positive @RequestParam int size) {
//        Page<Message> pageMessages = messageService.findMessages(page - 1, size);
//        List<Message> messages = pageMessages.getContent();
//        List<MessageResponseDto.Post> responses = messageMapper.messagesToMessageResPostDtos(messages);
//
//        return new ResponseEntity<>(
//                new MultiResponseDto<>(responses, pageMessages),
//                HttpStatus.OK
//        );
//    }



//    @PatchMapping("/{message-id}")
//    public ResponseEntity patchMessage(@Positive @PathVariable("message-id") long messageId,
//            @Valid @RequestBody MessageRequestDto.Patch patch) {
//        patch.setMessageId(messageId);
//        Message message = messageService.updateMessage(messageMapper.messageReqPatchDtoToMessage(patch));
//        MessageResponseDto.Post response = messageMapper.messageToMessageResPostDto(message);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(response), HttpStatus.OK
//        );
//    }

//    @DeleteMapping("/{message-id}")
//    public ResponseEntity deleteMessage(@Positive @PathVariable("message-id") long messageId) {
//        messageService.deleteMessage(messageId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
}