package codestates.main22.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://seb41-main-022.vercel.app/", "http://localhost:3000")
                .allowedMethods("*")
//                .exposedHeaders("Authorization", "Refresh")
                .allowCredentials(false)
                .maxAge(3000);

    }
}
