package database;

import consts.Env;
import lombok.SneakyThrows;
import util.ExecutionVariables;
import util.PropertyLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbConnector {

    private String dbLink;
    private String dbUser;
    private String dbPassword;

    private void setDbCredentials() {
        Env env = ExecutionVariables.getEnv();
        switch (env) {
            case PROD:
                dbLink = PropertyLoader.loadProperty("/credentials.properties", "prod-db-link");
                dbUser = PropertyLoader.loadProperty("/credentials.properties", "prod-db-user");
                dbPassword = PropertyLoader.loadProperty("/credentials.properties", "prod-db-password");
                break;
            default:
                throw new IllegalStateException("Unexpected environment: " + env);
        }
    }

    /**
     * https://metanit.com/java/database/2.4.php
     * Do not forget to close connection: conn.close();
     */
    @SneakyThrows
    public Connection connectToDatabase() {
        Connection connection;
        setDbCredentials();

        Class.forName("com.mysql.cj.jdbc.Driver");

        Properties info = new Properties();
        info.put("user", dbUser);
        info.put("password", dbPassword);

        connection = DriverManager.getConnection(dbLink, info);
        assert connection != null;
        return connection;
    }

}