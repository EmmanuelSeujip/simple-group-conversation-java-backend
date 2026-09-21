package com.backend.messagerie.service.user;

import com.backend.messagerie.models.User;
import com.backend.messagerie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }

    public List<User> getOnlineUser(){
        return userRepository.findByOnlineTrue();
    }

    public void setUserOffline(User user){
        user.setOnline(false);
        userRepository.save(user);
    }
    public boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    public Long getIdByUsername(String username){
        if (!existByUsername(username)){
            throw new IllegalArgumentException("Aucun utilisateur trouvé");
        }
        User user=findByUsername(username);
        return user.getId();
    }
}
