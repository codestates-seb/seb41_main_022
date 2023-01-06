package codestates.main22.chat.controller;

import codestates.main22.chat.dto.ChatDto;
import codestates.main22.chat.entity.ChatEntity;
import codestates.main22.chat.mapper.ChatMapper;
import codestates.main22.chat.service.ChatService;
import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/chat")
@Validated
@AllArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    //CRUD 순서에 맞춰서

    //CREATE
    @PostMapping
    public ResponseEntity postChat(@Valid @RequestBody ChatDto.Post post) {
        ChatEntity chat = chatService.createChat(chatMapper.chatPostDtoToChatEntity(post));

        ChatDto.Response responseDto = chatMapper.chatEntityToResponseCheck(chat);
        SingleResponseDto<ChatDto.Response> response = new SingleResponseDto<>(responseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //READ - 하나 조회
    @GetMapping("/{chat-id}")
    public ResponseEntity getChat(@Positive @PathVariable("chat-id") long chatId) {
        ChatEntity chat = chatService.findChat(chatId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        chatMapper.chatEntityToResponseCheck(chat)),HttpStatus.OK);
    }

    //READ - 전체 조회
    @GetMapping
    public ResponseEntity getChats(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<ChatEntity> pageChats = chatService.findChats(page - 1, size);
        List<ChatEntity> chats = pageChats.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>(
                        chatMapper.chatsToResponse(chats),pageChats),HttpStatus.OK);
    }

    //UPDATE
    @PatchMapping("/{chat-id}")
    public ResponseEntity patchChat(@Positive @PathVariable("chat-id") long chatId,
                                    @Valid @RequestBody ChatDto.Patch patch) {
        ChatEntity chat =
                chatService.updateChat(chatId, chatMapper.chatPatchDtoToChatEntity(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        chatMapper.chatEntityToResponseCheck(chat)),HttpStatus.OK);

    }

    //DELETE
    @DeleteMapping("/{chat-id}")
    public ResponseEntity deleteChat(@PathVariable("chat-id") @Positive long chatId) {
        chatService.deleteChat(chatId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
