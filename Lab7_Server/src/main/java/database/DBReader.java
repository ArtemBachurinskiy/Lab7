package database;

import collection.CollectionManager;
import entities.*;
import output.OutputManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class DBReader {
    private Connection connection;
    private CollectionManager collectionManager;
    private Statement statement;
    private OutputManager outputManager;

    public DBReader (DBConnector dbConnector, CollectionManager collectionManager, OutputManager outputManager) {
        this.connection = dbConnector.getConnection();
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    public void readDBEntitiesTable() {
        String select = "SELECT * FROM MOVIES;";
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось сформировать SQL запрос к базе данных...");
        }
        try {
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                Integer id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                Integer x = Integer.parseInt(rs.getString("COORDINATE_X"));
                Double y = Double.parseDouble(rs.getString("COORDINATE_Y"));
                LocalDateTime creationDate = LocalDateTime.parse(rs.getString("CREATION_DATE"));
                int oscarsCount = Integer.parseInt(rs.getString("OSCARS_COUNT"));
                long goldenPalmCount = Long.parseLong(rs.getString("GOLDEN_PALM_COUNT"));
                String tagline = rs.getString("TAGLINE");
                MovieGenre genre = MovieGenre.valueOf(rs.getString("GENRE"));
                String operator_name = rs.getString("PERSON_NAME");
                ZonedDateTime birthday = ZonedDateTime.parse(rs.getString("PERSON_BIRTHDAY"));
                Integer weight = Integer.parseInt(rs.getString("PERSON_WEIGHT"));
                String passportID = rs.getString("PERSON_PASSPORT_ID");
                Color hairColor = Color.valueOf(rs.getString("PERSON_HAIR_COLOR"));

                Person operatorFromDB = new Person(operator_name, birthday, weight, passportID, hairColor);
                Movie movieFromDB = new Movie(id, name, new Coordinates(x,y), creationDate, oscarsCount, goldenPalmCount, tagline, genre, operatorFromDB);
                collectionManager.insertMovie(name, movieFromDB);
            }
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось прочитать содержимое таблицы базы данных...");
        }
    }
}
