package codestates.main22.user.controller;

import codestates.main22.user.mapper.UserMapper;
import codestates.main22.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
}
