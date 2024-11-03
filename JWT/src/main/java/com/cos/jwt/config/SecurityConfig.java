package com.cos.jwt.config;

import com.cos.jwt.filter.MyFilter1;
import com.cos.jwt.filter.MyFilter3;
import com.cos.jwt.jwt.JwtAuthenticationFilter;
import com.cos.jwt.jwt.JwtAuthorizationFilter;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured()어노테이션 활성화
public class SecurityConfig {

    private final CorsFilter corsFilter;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(this.userDetailsService);
        AuthenticationManager manager = builder.build();
        http.authenticationManager(manager);

        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //BasicAuthenticationFilter는 시큐리티 계층클래스중 하나임 찾아보면됨
//                .addFilterBefore(new MyFilter3(), UsernamePasswordAuthenticationFilter.class)
                .addFilter(corsFilter) // @CrossOrigin(인증 x), 시큐리티 필터에 등록 인증 (O)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(new JwtAuthenticationFilter(manager)) // AuthenticationManger
                .addFilter(new JwtAuthorizationFilter(manager, userRepository)) // AuthenticationManger
                //h2 db사용시 xframe설정 (다른 db사용시 해당 라인 지워도됨)
                .headers((httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable())))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/api/v1/user/**").hasAnyRole("USER")
                                //h2 db사용시 권한 허용 설정(다른 db사용시 해당 라인 지워도됨)
                                .requestMatchers("/h2-console/**").permitAll()
                                .requestMatchers("/api/v1/manager/**").hasAnyRole( "MANAGER", "ADMIN")
                                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()
                ).build();
    }
}
