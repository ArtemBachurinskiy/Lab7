package database;

import output.OutputManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private OutputManager outputManager;
    private Connection connection;

    public DBConnector(OutputManager outputManager) {
        this.outputManager = outputManager;
    }

    public void connect () {
        this.connection = null;
        final String DB_DRIVER = "org.postgresql.Driver";
        final String DB_URL = "jdbc:postgresql://localhost:5432/studs";
        final String USER = "postgres";
        final String PASSWORD = "admin";

        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            outputManager.printlnErrorMessage(e.getMessage());
        }

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            outputManager.printlnMessage("Установлено соединение с базой данных!");
        } catch (SQLException e) {
            outputManager.printlnErrorMessage(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}