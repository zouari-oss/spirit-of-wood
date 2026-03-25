package org.zouari.spiritofwood.bootstrap;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public final class DatabaseSchemaInitializer implements ApplicationRunner {

  private final DataSource dataSource;

  public DatabaseSchemaInitializer(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      if (!isPostgres(connection)) {
        return;
      }

      if (!articlesTableExists(connection)) {
        return;
      }

      try (Statement statement = connection.createStatement()) {
        statement.execute("ALTER TABLE articles ALTER COLUMN image TYPE TEXT");
      }
    }
  }

  private boolean isPostgres(Connection connection) throws SQLException {
    final String name = connection.getMetaData().getDatabaseProductName();
    return name != null && name.toLowerCase().contains("postgresql");
  }

  private boolean articlesTableExists(Connection connection) throws SQLException {
    final DatabaseMetaData metaData = connection.getMetaData();

    try (ResultSet tables = metaData.getTables(null, null, "articles", new String[] { "TABLE" })) {
      if (tables.next()) {
        return true;
      }
    }

    try (ResultSet tables = metaData.getTables(null, null, "ARTICLES", new String[] { "TABLE" })) {
      return tables.next();
    }
  }
}
