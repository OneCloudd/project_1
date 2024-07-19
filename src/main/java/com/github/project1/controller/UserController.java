package com.github.project1.controller;

import com.github.project1.dto.user.*;
import com.github.project1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        userService.signup(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(new SignupResponse("회원가입이 완료되었습니다."));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getEmail(), request.getPassword());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token); // 응답 헤더에 토큰 추가
        return new ResponseEntity<>(new LoginResponse("로그인이 성공적으로 완료되었습니다."), headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest request) {
        userService.logout(request.getEmail());
        return ResponseEntity.ok(new LogoutResponse("로그아웃이 완료되었습니다."));
    }


}
