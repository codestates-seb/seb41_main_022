package codestates.main22.chat.service;

import codestates.main22.chat.entity.Chat;
import codestates.main22.chat.repository.ChatRepository;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.study.entity.Study;
import codestates.main22.study.service.StudyService;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.repository.UserRepository;
import codestates.main22.utils.Init;
import codestates.main22.utils.Token;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final StudyService studyService;

    private final UserRepository userRepository;
    private final Token token;

    String secretChatUserImgUrl = Init.S3Url + "icons8-lock-50.png";
    String SecretChatUserName = "Secret";

    public ChatService(ChatRepository chatRepository,
                       StudyService studyService,
                       UserRepository userRepository,
                       Token token) {
        this.chatRepository = chatRepository;
        this.studyService = studyService;
        this.userRepository = userRepository;
        this.token = token;
    }

    //CRUD 순서에 맞춰서

    //CREATE
    @Transactional
    public Chat createChat(long studyId, Chat chat, UserEntity user) {
        Study findStudy = studyService.findStudy(studyId);
        chat.setChatUserId(user.getUserId());
        chat.setStudy(findStudy);

        return chatRepository.save(chat);
    }


    // 토큰값으로 user 조회
    public UserEntity findUserByToken(HttpServletRequest request) {
        return token.findByToken(request);
    }

    // Study 별 Chat 조회
    public Page<Chat> findByStudy(long studyId, int page, int size) {
        Study findStudy = studyService.VerifiedStudy(studyId);
        return chatRepository.findByStudy(findStudy, PageRequest.of(page, size, Sort.by("chatId").descending()));
    }

    // study별 chat에서 공개여부에 따른 필터링
    public List<Chat> filterByIsClosedChat(List<Chat> chats, HttpServletRequest request) {
        UserEntity user = token.findByToken(request);
        long studyId = Optional.ofNullable(chats.get(0)).get().getStudy().getStudyId();

        // 1. isClosedChat = false 인 경우 : 있던 chat 출력
        // 2. isClosedChat = true 인 경우
        // 1. chat 작성자인 경우 : 있던 chat 출력
        // 2. study 장인 경우 : 있던 chat 출력
        // 3. 그 외의 경우 : Secret chat -> isClosedChat=true이고, chat 작성자가 아니고, study 장이 아닌 경우

        return chats.stream().map(chat -> {
            // Secret chat 반환 -> isClosedChat=true이고, chat 작성자가 아니고, study 장이 아닌 경우
            if(chat.getIsClosedChat() &&
                    chat.getChatUserId() != user.getUserId() &&
                    !user.getRole().contains("STUDY" + studyId + "_ADMIN")){

                return makeSecretChat(chat);
            }
            else return chat;

        }).collect(Collectors.toList());

//        return chats;
    }

    // 필요한 List<User> 반환
    public Map<Long, UserEntity> findUsers(List<Chat> chats) {
        Map<Long, UserEntity> users = new HashMap<>();

        for (Chat chat : chats) {
            // secret chat인 경우는 user 저장 X
            if(chat.getChatUserId() == 0) continue;

            UserEntity user = userRepository.findById(chat.getChatUserId()).get();
            if (!users.containsKey(chat.getChatUserId())) users.put(chat.getChatUserId(), user);

            chat.getAnswers().stream().forEach(answer -> {
                UserEntity user1 = userRepository.findById(answer.getAnswerUserId()).get();
                if (!users.containsKey(answer.getAnswerUserId())) users.put(answer.getAnswerUserId(), user1);
            });
        }

        // secret user 생성
        UserEntity user = makeSecretUser();
        users.put(user.getUserId(), user);

        return users;
    }

    // Secret Chat 생성
    public Chat makeSecretChat(Chat chat) {
        Chat secretChat = new Chat();
        secretChat.setChatId(chat.getChatId());
        secretChat.setChatUserId(0);
        secretChat.setContent(this.SecretChatUserName);
        secretChat.setIsClosedChat(true);
        secretChat.setCreatedAt(chat.getCreatedAt());

        return secretChat;
    }

    // Secret User 생성
    public UserEntity makeSecretUser() {
        UserEntity user = new UserEntity();
        user.setUserId(0);
        user.setUsername(SecretChatUserName);
        user.setImgUrl(secretChatUserImgUrl);

        return user;
    }

    //READ - 하나 조회
    public Chat findChat(long chatId) {
        return verifiedChat(chatId); // chat 이 있는지 검증
    }

    //READ - 전체 조회
    public Page<Chat> findChats(int page, int size) {
        return chatRepository.findAll(
                PageRequest.of(page, size, Sort.by("chatId").descending())
        );
    }

    //UPDATE
    public Chat updateChat(long chatId, Chat changedChat) {
        Chat chat = verifiedChat(chatId); // chat 이 있는지 검증

        chat.setContent(changedChat.getContent());
        chat.setIsClosedChat(changedChat.getIsClosedChat());

        return chatRepository.save(chat);
    }

    //DELETE
    public void deleteChat(long chatId) {
        Chat chat = verifiedChat(chatId); // chat 이 있는지 검증
        chatRepository.delete(chat);
    }

    // chat 이 있는지 검증
    public Chat verifiedChat(long chatId) { // 해당 chatId를 사용하고 있는 채팅이 존재하는가?
        Optional<Chat> chat = chatRepository.findById(chatId);
        return chat.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHAT_NOT_FOUND));
    }
}
