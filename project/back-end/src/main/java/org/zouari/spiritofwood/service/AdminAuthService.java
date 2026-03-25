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

  public AdminAuthService(AdminUserRepository adminUserRepository) {
    this.adminUserRepository = adminUserRepository;
  }

  public String login(String email, String password) {
    if (email == null || password == null || email.isBlank() || password.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email and password are required");
    }

    final AdminUser adminUser = adminUserRepository.findByEmail(email.trim().toLowerCase())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

    if (!passwordEncoder.matches(password, adminUser.getPasswordHash())) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }

    final String token = UUID.randomUUID().toString();
    activeTokens.add(token);
    return token;
  }

  public String hashPassword(String plainPassword) {
    return passwordEncoder.encode(plainPassword);
  }

  public void validateBearerToken(String authorizationHeader) {
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
