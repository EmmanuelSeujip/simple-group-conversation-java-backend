package com.backend.messagerie.service.user.auth;

import com.backend.messagerie.dto.user.auth.AuthResponse;
import com.backend.messagerie.dto.user.auth.LoginRequest;
import com.backend.messagerie.dto.user.auth.RegisterRequest;
import com.backend.messagerie.models.User;
import com.backend.messagerie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username déjà utilisé");
        }
        String couleurAleatoire = getCouleurAleatoire();
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .avatarColor(couleurAleatoire)
                .lastSeen(LocalDateTime.now())
                .isOnline(true)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    private static @NonNull String getCouleurAleatoire() {
        String[] couleurs = {
                "#e11d48",
                "#2563eb",
                "#16a34a",
                "#f59e0b",
                "#9333ea",
                "#0891b2",
                "#db2777",
                "#65a30d",
                "#dc2626",
                "#7c3aed",
                "gray"
        };

        // Création d'un objet Random
        Random random = new Random();

        // Sélection aléatoire d'un index entre 0 et couleurs.length - 1
        int index = random.nextInt(couleurs.length);

        // Récupération de la couleur
        String couleurAleatoire = couleurs[index];
        return couleurAleatoire;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur introuvable"));

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
