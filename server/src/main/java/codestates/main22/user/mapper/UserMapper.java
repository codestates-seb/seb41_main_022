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
}
