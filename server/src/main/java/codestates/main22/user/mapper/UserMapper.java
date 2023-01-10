package codestates.main22.user.mapper;

import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

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
    List<UserDto.StudyUserResponse> usersToStudyUserResponse(List<UserEntity> users); // 스터디에 포함된 유저들 이름, 이미지, 역할만 호출
}
