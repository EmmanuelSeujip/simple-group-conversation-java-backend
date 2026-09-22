package com.backend.messagerie.controller.user;

import com.backend.messagerie.dto.user.UserDto;
import com.backend.messagerie.dto.user.auth.AuthResponse;
import com.backend.messagerie.dto.user.auth.LoginRequest;
import com.backend.messagerie.dto.user.auth.RegisterRequest;
import com.backend.messagerie.mapper.UserMapper;
import com.backend.messagerie.service.user.UserService;
import com.backend.messagerie.service.user.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        return ResponseEntity.ok(userMapper.cleanUserModel(userService.findByUsername(authentication.getName())));
    }


}
