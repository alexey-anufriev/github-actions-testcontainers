package test;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PostgresqlTest {

    @Test
    public void simpleDbTest() throws SQLException {
        QueryRunner runner = new QueryRunner(prepareDataSource());

        runner.update("CREATE TABLE test_table (data int)");
        runner.update("INSERT INTO test_table(data) VALUES(42)");

        int result = getTestResult(runner);

        Assertions.assertEquals(42, result);
    }

    private DataSource prepareDataSource() {
        PostgreSQLContainer databaseContainer = new PostgreSQLContainer()
                .withDatabaseName("test")
                .withUsername("admin")
                .withPassword("password");

        databaseContainer.start();

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(databaseContainer.getJdbcUrl());
        hikariConfig.setUsername("admin");
        hikariConfig.setPassword("password");

        return new HikariDataSource(hikariConfig);
    }

    private int getTestResult(QueryRunner runner) throws SQLException {
        return runner.query("SELECT data FROM test_table", rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

}
