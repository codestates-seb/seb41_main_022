package codestates.main22.oauth2.handler;

import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;

public class OAuth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;

    public OAuth2UserSuccessHandler(JwtTokenizer jwtTokenizer, CustomAuthorityUtils authorityUtils, UserService userService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = String.valueOf(oAuth2User.getAttributes().get("email"));
        UserEntity user = userService.findByEmail(email);
        List<String> authorities = authorityUtils.createRoles(email);

        // email로 가입된 사람이 없는 경우
        if(user == null) {
            String name = String.valueOf(oAuth2User.getAttributes().get("name"));
            String imgUrl = String.valueOf(oAuth2User.getAttributes().get("picture"));
            user = saveUser(email, name, imgUrl);
        }

        // 콘솔 출력 코드
//        oAuth2User.getAttributes().forEach((s, o) -> System.out.println("!! " + s + " : " + String.valueOf(o)));
//        oAuth2User.getAuthorities().stream().forEach(grantedAuthority -> System.out.println("!! granted : " + grantedAuthority.getAuthority()));
//        System.out.println("!! url : " + request.getRequestURI());

        redirect(request, response, user, authorities);
    }

    private UserEntity saveUser(String email, String name, String imgUrl) {
        UserEntity user = new UserEntity(email, name, imgUrl);
        return userService.createUser(user);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response,
                          UserEntity user,
                          List<String> authorities) throws IOException {
        String accessToken = "Bearer " + delegateAccessToken(user.getUsername(), user.getEmail(), authorities);
        String refreshToken = delegateRefreshToken(user.getEmail());

        // ---------------------------------------------------------------------------
        // response body 안에 token 값 저장
        saveTokenInResponseBody(response, accessToken, refreshToken);

        // response header 안에 token 값 저장
        response.setHeader("access-Token", accessToken);
        response.setHeader("refresh-Token", refreshToken);

        // access token 저장
        user.setToken(accessToken);
        userService.updateUser(user);

        // 콘솔 출력 코드
//        System.out.println("!! url : " + response.getHeaderNames().toString());
//        System.out.println("!! " + response.getHeader("Authorization"));
        // ---------------------------------------------------------------------------

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    // response body 안에 token 값 저장
    private void saveTokenInResponseBody(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        // response.body 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        // Token을 TokenResponse로 변환
        TokenResponse tokenResponse = new TokenResponse(
                accessToken,
                refreshToken
        );

        // json 형식으로 변환
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(tokenResponse);
        response.getWriter().write(result);
    }

    // access token 생성
    private String delegateAccessToken(String name, String email, List<String> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", name);
        claims.put("roles", authorities);

        String subject = email;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        return accessToken;
    }

    // refresh token 생성
    private String delegateRefreshToken(String email) {
        String subject = email;
        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getRefreshTokenExpirationMinutes());
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, expiration, base64EncodedSecretKey);

        return refreshToken;
    }

    // 리다이렉트 URL 생성
    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access-Token", accessToken);
        queryParams.add("refresh-Token", refreshToken);

        // backend local test
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
//                .port(8080)
                .path("/receive-token.html")
                .queryParams(queryParams)
                .build()
                .toUri();

        // frontend local test
//        return UriComponentsBuilder
//                .newInstance()
//                .scheme("http")
//                .host("localhost")
////                .port(3000)
////                .path("/receive-token.html")
//                .queryParams(queryParams)
//                .build()
//                .toUri();

        // S3 배포 시 : http://seb41-main-022.s3-website.ap-northeast-2.amazonaws.com/
//        return UriComponentsBuilder
//                .newInstance()
//                .scheme("http")
//                .host("seb41-main-022.s3-website.ap-northeast-2.amazonaws.com/")
////                .path("/receive-token.html")
//                .queryParams(queryParams)
//                .build()
//                .toUri();
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public class UserResopnse {
        private long userId;
        private String name;
        private String email;
    }

    @AllArgsConstructor
    @Setter
    @Getter
    public class TokenResponse {
        private String accessToken;
        private String refreshToken;
    }
}
