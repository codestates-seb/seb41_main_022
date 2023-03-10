package codestates.main22.oauth2.filter;

import codestates.main22.exception.BusinessLogicException;
import codestates.main22.exception.ExceptionCode;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.user.entity.UserEntity;
import codestates.main22.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;

    public JwtVerificationFilter(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 UserService userService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
         System.out.println("# JwtVerificationFilter");

        check(request);
        try {
            Map<String, Object> claims = verifyJws(request);
            setAuthenticationToContext(claims);
        } catch (SignatureException se) { // signature ??????
            request.setAttribute("exception", se);
        } catch (ExpiredJwtException ee) { // ?????? ??????
            request.setAttribute("exception", ee);

            /*
                 access-Token??? ??????????????? ??????????????? ???,
                 refresh-Token??? ???????????? access-Token ?????????
                 ???, refresh-Token??? ???????????? ???????????? ??????
            */

            verifyRefreshToken(request, response);

        } catch (Exception e) { // ??? ?????? ??????
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // refresh ?????? ????????? ??????
    protected void verifyRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            // ????????? AccessToken ?????? ??? ??????
            String accessToken = delegateAccessTokenByRefreshToken(request);
            response.setHeader("access-Token", accessToken);
            request.setAttribute("new-access-Token", accessToken);
        } catch (SignatureException se) {
            request.setAttribute("exception", se);
            System.out.println("!! RefreshToken??? signiture??? payload??? ??????????????? ??????");
        } catch (ExpiredJwtException eee) {
            request.setAttribute("exception", eee);
            System.out.println("!! RefreshToken ???????????? ??????");
        } catch (Exception e) {
            request.setAttribute("exception", e);
            System.out.println("!! RefreshToken??? header ??????????????? ??????");
        }
    }

    // refresh-Token??? ????????? access-Token ?????????
    private String delegateAccessTokenByRefreshToken(HttpServletRequest request) {
        // Refresh ?????? ????????? ??????
        String jws = request.getHeader("refresh-Token");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> refreshClaims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        // ????????? Access ?????? ??????
        UserEntity user = userService.findByEmail((String) refreshClaims.get("sub"));
        String subject = user.getEmail();
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getUsername());
        claims.put("roles", authorityUtils.createRoles(subject));

        Date expiration = jwtTokenizer.getTokenExpiration(jwtTokenizer.getAccessTokenExpirationMinutes());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, expiration, base64EncodedSecretKey);

        // ?????? ????????? access ????????? claims ?????? ??? context??? ??????
        Map<String, Object> newAccessTokenclaims = jwtTokenizer.getClaims(accessToken, base64EncodedSecretKey).getBody();
        setAuthenticationToContext(newAccessTokenclaims);

        // ?????? ????????? access-Token ????????????
        request.getHeader("access-Token");
        user.setToken("Bearer " + accessToken);
        userService.updateUser(user);

        return "Bearer " + accessToken;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("access-Token");

        return authorization == null || !authorization.startsWith("Bearer");
    }

    private Map<String, Object> verifyJws(HttpServletRequest request) {
        String jws = request.getHeader("access-Token").replace("Bearer ", "");
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Map<String, Object> claims = jwtTokenizer.getClaims(jws, base64EncodedSecretKey).getBody();

        return claims;
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        String username = (String) claims.get("username");
        List<GrantedAuthority> authorities = authorityUtils.createAuthorities((List)claims.get("roles"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    // access-Token??? ???????????? user ?????? ????????????
    private void check(HttpServletRequest request) {
        String token = request.getHeader("access-Token");
        String newToken = (String) request.getAttribute("new-access-Token");
        Optional<UserEntity> user = userService.findByToken(token);
        Optional<UserEntity> newUser = userService.findByToken(newToken);

        if(newToken == null) user.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        else newUser.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
    }
}