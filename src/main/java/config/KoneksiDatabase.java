package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/lapas";
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }

        return conn;
    }
}