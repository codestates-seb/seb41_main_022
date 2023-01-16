package codestates.main22.user.mapper;

import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity userPostDtoToUserEntity(UserDto.Post post);
    UserEntity userPatchDtoToUserEntity(UserDto.Patch patch);
    UserDto.Response userEntityToResponseCheck(UserEntity user);
    List<UserDto.Response> usersToResponse(List<UserEntity> users);
    UserDto.ImageResponse userEntityToImageResponseCheck(UserEntity user); //이미지만 호출
    UserEntity userNamePatchDtoToUserEntity(UserDto.NamePatch namePatch); //이름만 변경
    UserDto.NameResponse userEntityToNameResponse(UserEntity user); //이름만 호출
    UserDto.SearchUserResponse userEntityToSearchUserReponse(UserEntity user); // 유저 식별값, 이름, 이미지만 호출

    // 스터디에 포함된 유저들 이름, 이미지, 역할만 호출
    default List<UserDto.StudyUserResponse> usersToStudyUserResponse(List<UserEntity> users, String studyId) {
        List<UserDto.StudyUserResponse> responses = new ArrayList<>();

        users.forEach(user -> {
            String role = user.getRole().stream().filter(
                    allRole ->
                        allRole.indexOf(studyId) != -1
            ).collect(Collectors.toList()).get(0);

            UserDto.StudyUserResponse response = new UserDto.StudyUserResponse(
                    user.getUsername(),
                    user.getImgUrl(),
                    role
            );
            responses.add(response);
        });

        return responses;
    }
}
