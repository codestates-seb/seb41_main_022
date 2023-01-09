package codestates.main22.user.controller;

import codestates.main22.dto.MultiResponseDto;
import codestates.main22.dto.SingleResponseDto;
import codestates.main22.user.dto.UserDto;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.mapper.UserMapper;
import codestates.main22.user.service.UserService;
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
@RequestMapping("/user")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    //CRUD 순서에 맞춰서

    //CREATE
    @PostMapping
    public ResponseEntity postUser(@Valid @RequestBody UserDto.Post post) {
        UserEntity user = userService.createUser(userMapper.userPostDtoToUserEntity(post));

        UserDto.Response responseDto = userMapper.userEntityToResponseCheck(user);
        SingleResponseDto<UserDto.Response> response = new SingleResponseDto<>(responseDto);

        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    //READ - 하나 조회
    @GetMapping("/{user-id}")
    public ResponseEntity getUser(@Positive @PathVariable("user-id") long userId) {
        UserEntity user = userService.findUser(userId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        userMapper.userEntityToResponseCheck(user)),HttpStatus.OK);
    }

    //READ - 전체 조회
    @GetMapping
    public ResponseEntity getUsers(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size) {
        Page<UserEntity> pageUsers = userService.findUsers(page - 1 , size);
        List<UserEntity> users = pageUsers.getContent();

        return new ResponseEntity(
                new MultiResponseDto<>
                        (userMapper.usersToResponse(users),pageUsers),HttpStatus.OK);
    }

    //UPDATE
    @PatchMapping("/{user-id}")
    public ResponseEntity patchUser(@Positive @PathVariable("user-id") long userId,
                                    @Valid @RequestBody UserDto.Patch patch) {

        UserEntity user = userService.updateUser(
                userId, userMapper.userPatchDtoToUserEntity(patch));

        return new ResponseEntity<>(
                new SingleResponseDto<>(
                        userMapper.userEntityToResponseCheck(user)),HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/{user-id}")
    public ResponseEntity deleteUser(@PathVariable("user-id") @Positive long userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
