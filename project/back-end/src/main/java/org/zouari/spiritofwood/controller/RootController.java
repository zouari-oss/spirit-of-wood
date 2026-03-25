package org.zouari.spiritofwood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zouari.spiritofwood.config.AppProperties;

@RestController
public final class RootController {

  private final AppProperties appProperties;

  public RootController(final AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  @GetMapping("/")
  public String index() {
    return String.format("%s v%s api is UP!", appProperties.name(), appProperties.version());
  }
} // RootController final class
