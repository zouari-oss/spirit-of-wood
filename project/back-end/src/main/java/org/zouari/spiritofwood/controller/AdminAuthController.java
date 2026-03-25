package org.zouari.spiritofwood.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zouari.spiritofwood.model.AdminLoginRequest;
import org.zouari.spiritofwood.model.AdminLoginResponse;
import org.zouari.spiritofwood.service.AdminAuthService;

@RestController
@RequestMapping("/api/admin")
public final class AdminAuthController {

  private final AdminAuthService adminAuthService;

  public AdminAuthController(final AdminAuthService adminAuthService) {
    this.adminAuthService = adminAuthService;
  }

  @PostMapping("/login")
  public AdminLoginResponse login(@RequestBody final AdminLoginRequest request) {
    final String token = adminAuthService.login(request.email(), request.password());
    return new AdminLoginResponse(token);
  }
}
