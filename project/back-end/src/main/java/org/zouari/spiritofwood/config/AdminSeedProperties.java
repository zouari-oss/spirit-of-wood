package org.zouari.spiritofwood.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin.seed")
public record AdminSeedProperties(String email, String password) {
}
