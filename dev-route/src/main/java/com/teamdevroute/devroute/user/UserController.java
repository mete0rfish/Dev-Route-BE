package com.teamdevroute.devroute.user;

import com.teamdevroute.devroute.global.auth.ELoginProvider;
import com.teamdevroute.devroute.user.dto.UserCreateRequest;
import com.teamdevroute.devroute.user.dto.UserCreateResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.createUser(request);
        return ResponseEntity.created(URI.create("/users/" + response.id())).body(response);
    }

    @PostMapping("/login")
    public void login(
            @RequestBody UserLoginRequest loginRequest,
            HttpServletResponse response
    ) {
        String token = userService.login(loginRequest);
        log.info("토큰: " + token);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @GetMapping("/auth/kakao")
    public void kakaoCallback(@RequestParam("code") String code) {
        String accessToken = userService.getAccessToken(code, ELoginProvider.KAKAO);
    }
}
