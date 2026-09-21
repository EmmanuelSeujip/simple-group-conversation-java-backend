package com.backend.messagerie.controller.user;

import com.backend.messagerie.dto.user.UserDto;
import com.backend.messagerie.mapper.UserMapper;
import com.backend.messagerie.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AppController {
    public final UserService userService;
    public final UserMapper userMapper;
    @GetMapping("/online-user")
    public List<UserDto> getOnlineUser(){
        return userService.getOnlineUser()
                .stream()
                .map(userMapper::cleanUserModel)
                .toList();
    }
}
