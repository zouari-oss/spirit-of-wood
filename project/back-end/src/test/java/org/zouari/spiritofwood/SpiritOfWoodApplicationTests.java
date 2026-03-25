package org.zouari.spiritofwood;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.zouari.spiritofwood.config.AppProperties;
import org.zouari.spiritofwood.controller.RootController;

/**
 * Integration tests for the {@link RootController} using Spring Boot and
 * configuration properties.
 *
 * <p>
 * This test class verifies the behavior of the root endpoint ("/") in the
 * {@link RootController}.
 * It uses {@link AppProperties} injected from Spring Boot's configuration
 * system to ensure
 * the controller returns the correct application name and version in its
 * response.
 * </p>
 *
 * <ul>
 * <li>Verifies that the root endpoint returns HTTP 200 OK.</li>
 * <li>Checks that the response string correctly formats the app name and
 * version.</li>
 * <li>Demonstrates usage of {@link RestTestClient} for controller testing
 * without starting a full server.</li>
 * </ul>
 *
 * @author ZouariOmar (zouariomar20@gmail.com)
 * @version 1.0.0
 * @since 2026-03-25
 * @see RootController
 * @see AppProperties
 */
@SpringBootTest
@EnableConfigurationProperties(AppProperties.class)
public final class SpiritOfWoodApplicationTests {

  @Autowired
  private AppProperties appProperties;

  private RestTestClient restTestClient;

  /**
   * Tests the root ("/") endpoint of {@link RootController}.
   *
   * <p>
   * Verifies that:
   * </p>
   * <ul>
   * <li>The HTTP status code is 200 OK.</li>
   * <li>The response body matches the expected format using {@link AppProperties}
   * values.</li>
   * </ul>
   *
   * <p>
   * Example expected response: "MyApp v1.0.0 api is UP!" if the
   * {@link AppProperties} name is "MyApp"
   * and version is "1.0.0".
   * </p>
   *
   * @author ZouariOmar (zouariomar20@gmail.com)
   * @version 1.0.0
   * @since 2026-03-25
   *
   * @see RootController#index()
   */
  @Test
  void testRootController() {
    restTestClient.get()
        .uri("/")
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
        .isEqualTo(String.format("%s v%s api is UP!",
            appProperties.name(),
            appProperties.version()));
  }

  /**
   * Sets up the {@link RestTestClient} with the {@link RootController} before
   * each test.
   *
   * <p>
   * This ensures each test uses a fresh instance of the controller with proper
   * configuration properties.
   * </p>
   */
  @BeforeEach
  private void setup() {
    restTestClient = RestTestClient.bindToController(
        new RootController(appProperties)).build();
  }
} // SpiritOfWoodApplicationTests final class
