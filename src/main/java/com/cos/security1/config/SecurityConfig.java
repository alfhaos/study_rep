package com.cos.security1.config;

import com.cos.security1.auth.PrincipalDetailsService;
import com.cos.security1.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // IoC 빈(bean)을 등록
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //secured()어노테이션 활성화
public class SecurityConfig {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    
    //해당 메서드의 리턴되는 오브젝트를 ioC로 리턴해준다
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/manger/**").hasAnyRole( "MANAGER", "ADMIN")
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().permitAll()

                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/loginForm")
                                .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 대신 로그인을 진행해줌
                                .defaultSuccessUrl("/")
                )
                //네이버, 카카오 등 oauth2 로그인 추가시 필요 코드
                .oauth2Login(oauthLogin ->
                        oauthLogin.loginPage("/loginForm")
                        // 로그인 뒤 후처리 필요
                        // 1. 코드받기(인증) , 2. 엑세스토큰(권한), 3. 사용자 프로필 정보 가져오기
                        // 4-1. 그 정보를 토대로 회원가입을 자동으로 진행
                                .userInfoEndpoint(userInfoEndpointConfig ->
                                        userInfoEndpointConfig.userService(principalOauth2UserService))
                        ).build();
    }

}
