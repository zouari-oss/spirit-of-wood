package org.zouari.spiritofwood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.zouari.spiritofwood.config.AdminSeedProperties;
import org.zouari.spiritofwood.config.DotenvPropertyLoader;
import org.zouari.spiritofwood.config.AppProperties;

/**
 * Entry point for the Spirit of Wood Spring Boot application.
 *
 * <p>
 * This class bootstraps and launches the application using Spring Boot's
 * auto-configuration mechanism. It initializes the application context,
 * performs component scanning, and starts the embedded server (if configured).
 * </p>
 *
 * <p>
 * Main responsibilities include:
 * </p>
 * <ul>
 * <li>Bootstrapping the Spring Boot application</li>
 * <li>Triggering auto-configuration and component scanning</li>
 * <li>Starting the application context</li>
 * <li>Launching the embedded web server (e.g., Tomcat) when using web
 * starters</li>
 * </ul>
 *
 * @author ZouariOmar (zouariomar20@gmail.com)
 * @version 1.0.0
 * @since 2026-03-25
 *
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.boot.context.properties.ConfigurationPropertiesScan
 * 
 * @implNote This class is intentionally declared as {@code final} to prevent
 *           inheritance.
 *
 * @apiNote Run this class as a Java application to start the backend service.
 *
 *          <pre>{@code
 * // Example usage:
 * // Run from IDE or command line:
 * // ./gradlew bootRun
 * }</pre>
 */
@ConfigurationPropertiesScan(basePackageClasses = { AppProperties.class, AdminSeedProperties.class })
@SpringBootApplication
public final class SpiritOfWoodApplication {

  public static void main(final String[] args) {
    DotenvPropertyLoader.load();
    SpringApplication.run(SpiritOfWoodApplication.class, args);
  }
} // SpringBootApplication final class
