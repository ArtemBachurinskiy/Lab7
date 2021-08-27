package database;

import output.OutputManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private Connection connection;
    private final OutputManager outputManager;

    public DBManager(DBConnector dbConnector, OutputManager outputManager) {
        this.connection = dbConnector.getConnection();
        this.outputManager = outputManager;
    }

    public void createDBUsersTableIfNotExists() {
        final String create_table =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "username VARCHAR PRIMARY KEY, " +
                        "password VARCHAR" +
                        ");";
        try {
            Statement statement = connection.createStatement();
            statement.execute(create_table);
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось создать таблицу USERS...");
        }
    }

    public void createDBEntitiesTableIfNotExists() {
        final String create_table =
                "CREATE TABLE IF NOT EXISTS movies (" +
                        "id VARCHAR PRIMARY KEY, " +
                        "name VARCHAR, " +
                        "coordinate_x VARCHAR, " +
                        "coordinate_y VARCHAR, " +
                        "creation_date VARCHAR, " +
                        "oscars_count VARCHAR, " +
                        "golden_palm_count VARCHAR, " +
                        "tagline VARCHAR, " +
                        "genre VARCHAR, " +
                        "person_name VARCHAR, "+
                        "person_birthday VARCHAR, " +
                        "person_weight VARCHAR, " +
                        "person_passport_id VARCHAR, " +
                        "person_hair_color VARCHAR, " +
                        "username VARCHAR REFERENCES users(username)" +
                        ");";
        final String create_sequence =
                "CREATE SEQUENCE IF NOT EXISTS id_sequence\n" +
                        "AS BIGINT\n" +
                        "INCREMENT 1\n" +
                        "START 1\n" +
                        "OWNED BY movies.id;";
        try {
            Statement statement = connection.createStatement();
            statement.execute(create_table);
            statement.execute(create_sequence);
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось создать таблицу MOVIES...");
        }
    }
}
