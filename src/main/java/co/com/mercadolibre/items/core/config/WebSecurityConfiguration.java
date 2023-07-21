package co.com.mercadolibre.items.core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
class WebSecurityConfiguration {

    private static final String[] SWAGGER_PATHS = {"/api/swagger-ui.html", "/api/swagger-ui/**", "/api/v3/api-docs/**", "/api/swagger-ui/**", "/api/webjars/swagger-ui/**"};

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(SWAGGER_PATHS).anyRequest();
    }
}