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

    public void createDBEntitiesTableIfNotExists() {
        final String create_table =
                "CREATE TABLE IF NOT EXISTS MOVIES (" +
                        "ID VARCHAR," +
                        "NAME VARCHAR," +
                        "COORDINATE_X VARCHAR," +
                        "COORDINATE_Y VARCHAR," +
                        "CREATION_DATE VARCHAR," +
                        "OSCARS_COUNT VARCHAR," +
                        "GOLDEN_PALM_COUNT VARCHAR," +
                        "TAGLINE VARCHAR," +
                        "GENRE VARCHAR," +
                        "PERSON_NAME VARCHAR,"+
                        "PERSON_BIRTHDAY VARCHAR," +
                        "PERSON_WEIGHT VARCHAR," +
                        "PERSON_PASSPORT_ID VARCHAR," +
                        "PERSON_HAIR_COLOR VARCHAR);";
        final String create_sequence =
                "CREATE SEQUENCE IF NOT EXISTS id_sequence\n" +
                        "AS BIGINT\n" +
                        "INCREMENT 1\n" +
                        "START 1\n" +
                        "OWNED BY MOVIES.ID;";
        try {
            Statement statement = connection.createStatement();
            statement.execute(create_table);
            statement.execute(create_sequence);
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось создать таблицу...");
        }
    }
}
