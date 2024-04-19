package io.security.oauth2.springsecurityoauth2.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorRequest) ->
                        authorRequest.anyRequest().authenticated());
//        http.exceptionHandling((exceptionHandling) ->
//                exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
//                    System.out.println("custom entryPoint");
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                }));
        http.httpBasic(Customizer.withDefaults())
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.permitAll());
//                .apply(new CustomSecurityConfigurer().setFlag(false));
        return http.build();
    }

}
