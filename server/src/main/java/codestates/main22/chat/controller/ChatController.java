package codestates.main22.chat.controller;

import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.Chat;
import codestates.main22.chat.mapper.ChatMapper;
import codestates.main22.chat.service.ChatService;
import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
@Validated
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    //CRUD 순서에 맞춰서

    // studyHall/main 채팅 작성(아래)
    @PostMapping
    public ResponseEntity postChat(@Valid @RequestBody ChatDto.Post post,
                                   @Positive @RequestParam long studyId,
                                   HttpServletRequest request) {
        UserEntity user = chatService.findUserByToken(request);
        Chat chat = chatService.createChat(studyId, chatMapper.chatPostDtoToChat(post), user);
        ChatDto.Response response = chatMapper.chatToResponseCheck(chat, user);
        response.setChatCreatedAt(chat.getCreatedAt());

        return new ResponseEntity<>(new SingleResponseDto(response), HttpStatus.CREATED);
    }

    // studyHall/main 채팅 조회(아래)
    @GetMapping("/{study-id}")
    public ResponseEntity getStudyChats(@PathVariable("study-id") @Positive long studyId,
                                        @Positive @RequestParam int page,
                                        @Positive @RequestParam int size,
                                        HttpServletRequest request) {
        Page<Chat> studyChats = chatService.findByStudy(studyId, page-1, size);
        List<Chat> chats = chatService.filterByIsClosedChat(studyChats.getContent(), request);
        Map<Long, UserEntity> users = chatService.findUsers(chats);

//        List<ChatDto.Response> responses = chatMapper.chatsToResponse(chats);
        List<ChatDto.Response> responses = chatMapper.chatsToResponse(chats, users);
        return new ResponseEntity<>(
                new MultiResponseDto<>(responses, studyChats), HttpStatus.OK
        );
    }


    //READ - 하나 조회 // 일단 사용 안하고 있고, 해당 URL 사용을 위해 주석 처리
//    @GetMapping("/{chat-id}")
//    public ResponseEntity getChat(@Positive @PathVariable("chat-id") long chatId) {
//        ChatEntity chat = chatService.findChat(chatId);
//
//        return new ResponseEntity<>(
//                new SingleResponseDto<>(
//                        chatMapper.chatEntityToResponseCheck(chat)),HttpStatus.OK);
//    }

    //READ - 전체 조회
//    @GetMapping
//    public ResponseEntity getChats(@Positive @RequestParam int page,
//                                   @Positive @RequestParam int size) {
//        Page<Chat> pageChats = chatService.findChats(page - 1, size);
//        List<Chat> chats = pageChats.getContent();
//
//        return new ResponseEntity(
//                new MultiResponseDto<>(
//                        chatMapper.chatsToResponse(chats),pageChats),HttpStatus.OK);
//    }

    //UPDATE
    @PatchMapping("/{chat-id}")
    public ResponseEntity patchChat(@Positive @PathVariable("chat-id") long chatId,
                                    @Valid @RequestBody ChatDto.Patch patch,
                                    HttpServletRequest request) {
        UserEntity user = chatService.findUserByToken(request);
        Chat chat = chatService.updateChat(chatId, chatMapper.chatPatchDtoToChat(patch));
        ChatDto.Response response = chatMapper.chatToResponseCheck(chat, user);

        return new ResponseEntity<>(new SingleResponseDto<>(response),HttpStatus.OK);

    }

    //DELETE
    @DeleteMapping("/{chat-id}")
    public ResponseEntity deleteChat(@PathVariable("chat-id") @Positive long chatId) {
        chatService.deleteChat(chatId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
