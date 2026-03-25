package org.zouari.spiritofwood.bootstrap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.zouari.spiritofwood.config.AdminSeedProperties;
import org.zouari.spiritofwood.model.AdminUser;
import org.zouari.spiritofwood.repository.AdminUserRepository;
import org.zouari.spiritofwood.service.AdminAuthService;

@Component
public final class AdminSeedInitializer implements ApplicationRunner {

  private final AdminSeedProperties adminSeedProperties;
  private final AdminUserRepository adminUserRepository;
  private final AdminAuthService adminAuthService;

  public AdminSeedInitializer(
      AdminSeedProperties adminSeedProperties,
      AdminUserRepository adminUserRepository,
      AdminAuthService adminAuthService) {
    this.adminSeedProperties = adminSeedProperties;
    this.adminUserRepository = adminUserRepository;
    this.adminAuthService = adminAuthService;
  }

  @Override
  public void run(ApplicationArguments args) {
    final String email = normalize(adminSeedProperties.email());
    final String password = adminSeedProperties.password();

    if (email == null || password == null || password.isBlank()) {
      return;
    }

    final boolean exists = adminUserRepository.findByEmail(email).isPresent();
    if (exists) {
      return;
    }

    final AdminUser adminUser = new AdminUser();
    adminUser.setEmail(email);
    adminUser.setPasswordHash(adminAuthService.hashPassword(password));
    adminUserRepository.save(adminUser);
  }

  private String normalize(String email) {
    if (email == null || email.isBlank()) {
      return null;
    }

    return email.trim().toLowerCase();
  }
}
