package codestates.main22.oauth2.handler;

import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    private final UserService userService;


    public UserLogoutSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        System.out.println("!! success logout");

        UserEntity user = userService.findByToken(request);
        user.setToken("");
        userService.updateUser(user);
    }
}
