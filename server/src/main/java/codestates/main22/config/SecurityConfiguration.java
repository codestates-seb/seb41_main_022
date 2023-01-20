package codestates.main22.config;

import codestates.main22.oauth2.filter.JwtVerificationFilter;
import codestates.main22.oauth2.handler.UserLogoutSuccessHandler;
import codestates.main22.oauth2.handler.OAuth2UserSuccessHandler;
import codestates.main22.oauth2.handler.UserAccessDeniedHandler;
import codestates.main22.oauth2.handler.UserAuthenticationEntryPoint;
import codestates.main22.oauth2.jwt.JwtTokenizer;
import codestates.main22.oauth2.utils.CustomAuthorityUtils;
import codestates.main22.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * JWT 검증 기능 추가
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
    private final JwtTokenizer jwtTokenizer;
    private final CustomAuthorityUtils authorityUtils;
    private final UserService userService;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer,
                                 CustomAuthorityUtils authorityUtils,
                                 UserService userService) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
//                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new UserAuthenticationEntryPoint())
                .accessDeniedHandler(new UserAccessDeniedHandler())
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .logout()
                .logoutUrl("/logout") // 로그아웃 처리 URL(기본값)
                .invalidateHttpSession(true) // 로그아웃 성공 시 세션 제거
                .clearAuthentication(true) // 로그아웃 시 권한 제거
                .permitAll() // 모두 허용
                .logoutSuccessHandler(new UserLogoutSuccessHandler(userService)) // 로그아웃 성공 후 핸들러
                .and()
                .authorizeHttpRequests(authorize -> authorize // url authorization 전체 추가
//                                .antMatchers(HttpMethod.POST, "/*/coffees").hasRole("ADMIN")
//                                .antMatchers(HttpMethod.PATCH, "/*/coffees/**").hasRole("ADMIN")
//                                .antMatchers(HttpMethod.GET, "/*/coffees/**").hasAnyRole("USER", "ADMIN")
//                                .antMatchers(HttpMethod.GET, "/*/coffees").permitAll()
//                                .antMatchers(HttpMethod.DELETE, "/*/coffees").hasRole("ADMIN")
//                                .antMatchers(HttpMethod.POST, "/*/orders").hasRole("USER")
//                                .antMatchers(HttpMethod.PATCH, "/*/orders").hasAnyRole("USER", "ADMIN")
//                                .antMatchers(HttpMethod.GET, "/*/orders/**").hasAnyRole("USER", "ADMIN")
//                                .antMatchers(HttpMethod.DELETE, "/*/orders").hasRole("USER")

//                                .antMatchers(HttpMethod.POST, "/study").hasAnyRole("USER", "ADMIN")
                                .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2UserSuccessHandler(jwtTokenizer, authorityUtils, userService))
                );

        return http.build();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PATCH", "DELETE"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

    // 추가
    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils, userService);

            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class); // (1)
        }
    }
}
