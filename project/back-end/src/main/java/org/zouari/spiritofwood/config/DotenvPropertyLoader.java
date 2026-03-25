package org.zouari.spiritofwood.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class DotenvPropertyLoader {

  private DotenvPropertyLoader() {
  }

  public static void load() {
    loadFrom(Path.of(".env"));
  }

  private static void loadFrom(Path dotenvPath) {
    if (!Files.exists(dotenvPath)) {
      return;
    }

    final List<String> lines;
    try {
      lines = Files.readAllLines(dotenvPath);
    } catch (IOException ignored) {
      return;
    }

    for (String line : lines) {
      final String trimmed = line.trim();
      if (trimmed.isEmpty() || trimmed.startsWith("#")) {
        continue;
      }

      final int separator = trimmed.indexOf('=');
      if (separator <= 0) {
        continue;
      }

      final String key = trimmed.substring(0, separator).trim();
      final String value = trimmed.substring(separator + 1).trim();
      if (!key.isEmpty() && System.getenv(key) == null && System.getProperty(key) == null) {
        System.setProperty(key, value);
      }
    }
  }
}
