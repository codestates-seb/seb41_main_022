package codestates.main22.utils;

import org.springframework.context.annotation.Configuration;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
public class CustomCookie {
    // 쿠키 생성 메서드
    public void createCookie(HttpServletResponse response, String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setPath("/");
//        cookie.setMaxAge(60 * 500 * 60); // 500분간 저장
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    // 로그인/회원가입의 버튼을 눌렀을 시, 쿠키값 저장하기
    public void createCookie(HttpServletRequest request, HttpServletResponse response) {
        if(request.getServletPath().equals("/oauth2/authorization/google") || request.getServletPath().equals("/oauth2/authorization/github")) {
            String[] url = request.getHeader("referer").split(":");
            createCookie(response, "urlProtocol", url[0]); // protocol 저장하기(ex. http)
            createCookie(response, "urlHost", url[1].split("//")[1].split("/")[0]); // host 저장하기
            if (url.length == 3) createCookie(response, "urlPort", url[2].split("/")[0]); // port 번호가 있으면 쿠키값 생성
        }
    }

    // URL 쿠키값 읽기
    public String[] readURLfromCookie(HttpServletRequest request) {
        String[] url = new String[3];
        url[2] = "";

        Arrays.stream(request.getCookies())
                .forEach(cookie -> {
                    System.out.println("!! cookie name : " + cookie.getName());
                    if(cookie.getName().equals("urlProtocol")) url[0] = cookie.getValue();
                    if(cookie.getName().equals("urlHost")) url[1] = cookie.getValue();
                    if(cookie.getName().equals("urlPort")) url[2] = cookie.getValue();
                });

        return url;
    }
}
