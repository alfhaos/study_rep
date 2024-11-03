package com.cos.jwt.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

// 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 있음
// /login 요청해서 username,과 password 전송하면
// UsernamePasswordAuthenticationFilter 동작을 하지만 formLogin을 disabled해서 따로 이렇게 구현함

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter 실행중");

        // 1.username, password 받아서
        try {
//            BufferedReader br = request.getReader();
//
//            String input = null;
//            while ((input = br.readLine()) != null){
//                System.out.println(input);
//            }

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // PrincalDetailsService의 loadByUsername() 함수가 실행됨
            // 실행 후 정상이면 authentication이 리턴됨
            // DB에 있는 username과 password가 일치한다.
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // authentication 객체가 session영역에 저장됨. => 로그인이 되었다는 뜻.
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername());

            // authentication 객체가 session영역에 저장을 해야하고 그 방법은 return
            // 리턴의 이유는 권한 관리를 s시큐리티가 대신 해주기 때문에 편하려고
            // JWT 토큰을 사용하면서 세션을 만들 이유가 없음, 근데 단지 권한 처리 떄문에 SESSION 넣어줌
            return authentication;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 2. 정상인지 로그인 시도 : authenticationManager 로그인 시도를 하면
        // PrincipalDetailsService가 호출됨 loadUserByUsername() 함수 실행됨

        // 3. princiaplDetails를 세션에 담고(권한 관리를 위해서)

        // 4. jwt토큰을 만들어서 응답해주면 됨.

    }

    //attemptAuthentication 실행 후 인증이 정상적으로 되었으면 successfulAuthentication 함수가 실행
    // JWT토큰을 만들어서 REQUEST요청한 사용자에게 JWT토큰을 reponse해주면 됨.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("successfulAuthentication 실행됨: d인증이 완료되었다는 뜻임.");
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // Hash 암호 방식
        String jwtToken = JWT.create()
                .withSubject("cos토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getUser().getId())
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING,  JwtProperties.TOKEN_PREFIX + jwtToken);
    }
}
