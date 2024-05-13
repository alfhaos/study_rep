package io.security.oauth2.springsecurityoauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authRequest -> authRequest
                .requestMatchers("/","/oauth2Login","/client","/logout").permitAll()
                .anyRequest().authenticated());
        http
//                .oauth2Login(Customizer.withDefaults())
                .oauth2Client(Customizer.withDefaults());
        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                .invalidateHttpSession(true)
                .deleteCookies("JESSIONID")
                .clearAuthentication(true));

        return http.build();
    }

}
