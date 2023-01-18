package codestates.main22.participant.service;

import codestates.main22.calendar.entity.Calendar;
import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.participant.entity.Participant;
import codestates.main22.participant.repository.ParticipantRepository;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final UserService userService;

    ParticipantService(ParticipantRepository participantRepository,
                       UserService userService) {
        this.participantRepository = participantRepository;
        this.userService = userService;
    }

    // 참가자 리스트 생성
    public List<Participant> createParticipants(Calendar calendar, long studyId) {
        // participant와 연관관계 연결
        List<Participant> participants = new ArrayList<>();
        List<UserEntity> users = userService.findByStudy(studyId);
        users.forEach(user -> {
            participants.add(createParticipant(user, calendar));
        });

        return participants;
    }

    // 참가자 생성
    public Participant createParticipant(UserEntity user, Calendar calendar) {
        Participant participant = new Participant(
                user.getUserId(),
                user.getUsername()
        );
        participant.setCalendar(calendar);
        return participantRepository.save(participant);
    }

    // 참가자 리스트 수정
    public List<Participant> updateParticipants(Calendar beforeCalendar, Calendar afterCalendar) {
        List<Participant> beforeParticipants = beforeCalendar.getParticipants();
        List<Participant> afterParticipants = afterCalendar.getParticipants();
        List<UserEntity> users = userService.findByStudy(beforeCalendar.getStudy().getStudyId()); // 스터디에 소속된 user들

        for (Participant participant : afterParticipants) {
            UserEntity user = userService.verifiedUser(participant.getUserId());

            // userId와 userName이 같은지 비교
            if (!user.getUsername().equals(participant.getUsername())) // 서로 다른 경우 에러 발생
                throw new BusinessLogicException(ExceptionCode.NOT_SAME);

            // 스터디에 소속된 user인지 비교
            if (!users.contains(user)) // 스터디에 소속되지 않은 user이면 에러 발생
                throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);

            // 이전 참가자 목록에 userId가 있으면 userId 반환, 아니면 0 반환
            long participantId = beforeParticipants.stream()
                    .filter(value -> value.getUserId() == user.getUserId())
                    .mapToLong(value -> value.getParticipantId())
                    .findFirst()
                    .orElse(0);

            // participant 저장
            if (participantId == 0L) { // 이전에 없는 참가자인 경우 -> 새로 생성하기
                updateParticipants(participant, afterCalendar).getParticipantId();
            } else { // 존재하던 참가자인 경우 -> 업데이트
                updateParticipants(participant, beforeCalendar, participantId);
                beforeParticipants.remove(participant);
            }
        }

        // 삭제해야할 participant
        for (Participant participant : beforeParticipants) {
            participantRepository.delete(participant);
        }

        return afterParticipants;
    }

    // 참가자 수정
    public Participant updateParticipants(Participant participant, Calendar calendar) {
        participant.setCalendar(calendar);

        return participantRepository.save(participant);
    }

    // 참가자 수정
    public Participant updateParticipants(Participant participant, Calendar calendar, long participantId) {
        participant.setParticipantId(participantId);
        participant.setCalendar(calendar);

        return participantRepository.save(participant);
    }

}
