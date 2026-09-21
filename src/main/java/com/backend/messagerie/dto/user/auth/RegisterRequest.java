package com.backend.messagerie.dto.user.auth;

import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size(min = 3) String username,
        @Size(min = 6) String password
) {}

