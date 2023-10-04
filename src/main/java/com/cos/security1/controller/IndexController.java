package com.cos.security1.controller;

import com.cos.security1.model.User;
import com.cos.security1.repositry.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    //시큐리티CONFIG에서 선언한 암호화..
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    @ResponseBody
    public String loginTest( @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("/test/login ==========================");
        System.out.println("au : " + userDetails.getUsername());

        return "세션 정보 확인하기 ";

    }

    @GetMapping({"","/"})
    public String index() {

        //머스테치 기본폴더 /리소스/템플릿스
        //뷰리졸버 설정 : templates (prefix), .mustache (suffix) 생략가능!
        return "index";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin";
    }
    @GetMapping("/manager")
    @ResponseBody
    public String manager() {
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }
    @PostMapping("/join")
    @ResponseBody
    public String join(User user) {
        System.out.println(user);
        
        user.setRole("USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        //회원가입 잘됨. 비밀번호 : 1234 => 시큐리티로 로그인 할수 없음 패스워드가 암호화가 되어있지 않기 때문
        //위처럼 암호화를 해야 시큐리티로 로그인 가능
        userRepository.save(user);
        
        return "join";
    }

    @Secured("ADMIN")
    @GetMapping("/info")
    @ResponseBody
    public String info() {
        return "개인정보";
    }

    @PreAuthorize("hasAnyRole('MANAGER')")
    @GetMapping("/data")
    @ResponseBody
    public String data() {
        return "개인정보";
    }

}
