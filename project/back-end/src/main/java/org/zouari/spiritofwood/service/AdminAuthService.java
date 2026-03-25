package org.zouari.spiritofwood.service;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.zouari.spiritofwood.model.AdminUser;
import org.zouari.spiritofwood.repository.AdminUserRepository;

@Service
public final class AdminAuthService {

  private final AdminUserRepository adminUserRepository;
  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
  private final Set<String> activeTokens = ConcurrentHashMap.newKeySet();

  public AdminAuthService(final AdminUserRepository adminUserRepository) {
    this.adminUserRepository = adminUserRepository;
  }

  public String login(final String email, final String password) {
    if (email == null || password == null || email.isBlank() || password.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
    }

    final AdminUser adminUser = adminUserRepository.findByEmail(email.trim().toLowerCase())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

    final String passwordHash = adminUser.getPasswordHash();
    if (passwordHash == null || passwordHash.isBlank()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    final boolean matches;
    try {
      matches = passwordEncoder.matches(password, passwordHash);
    } catch (IllegalArgumentException exception) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials", exception);
    }

    if (!matches) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    final String token = UUID.randomUUID().toString();
    activeTokens.add(token);
    return token;
  }

  public String hashPassword(final String plainPassword) {
    return passwordEncoder.encode(plainPassword);
  }

  public void validateBearerToken(final String authorizationHeader) {
    if (authorizationHeader == null || authorizationHeader.isBlank()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
    }

    if (!authorizationHeader.startsWith("Bearer ")) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authorization header format");
    }

    final String token = authorizationHeader.substring("Bearer ".length()).trim();
    if (token.isBlank() || !activeTokens.contains(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
    }
  }
}
