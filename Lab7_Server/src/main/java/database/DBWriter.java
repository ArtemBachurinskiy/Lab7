package database;

import collection.CollectionManager;
import entities.Movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class DBWriter {
    private Connection connection;
    private CollectionManager collectionManager;
    private Statement statement;
    private String message;

    public DBWriter (DBConnector dbConnector, CollectionManager collectionManager) {
        this.connection = dbConnector.getConnection();
        this.collectionManager = collectionManager;
    }

    public String writeCollectionToDB() {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            message = "Не удалось сформировать SQL запрос к базе данных...";
            return message;
        }
        boolean cleared = truncateDBEntitiesTable();
        if (cleared)
            insertEntitiesIntoDBTable();
        return message;
    }

    private boolean truncateDBEntitiesTable() {
        final String truncate_MOVIES = "TRUNCATE TABLE MOVIES";
        try {
            statement.execute(truncate_MOVIES);
            return true;
        } catch (SQLException e) {
            message = "Не удалось очистить устаревшие данные в таблице базы данных...";
            return false;
        }
    }

    private void insertEntitiesIntoDBTable() {
        boolean exception = false;
        message = "Коллекция была успешно сохранена в базу данных!";
        for (Map.Entry<String, Movie> collectionElement : collectionManager.getCollectionElements()) {
            Movie movie = collectionElement.getValue();
            String insert_into_MOVIES =
                    "INSERT INTO MOVIES VALUES ('" +
                            movie.getId() + "', '" +
                            movie.getName() + "', '" +
                            movie.getCoordinates().getX() + "', '" +
                            movie.getCoordinates().getY() + "', '" +
                            movie.getCreationDate() + "', '" +
                            movie.getOscarsCount() + "', '" +
                            movie.getGoldenPalmCount() + "', '" +
                            movie.getTagline() + "', '" +
                            movie.getGenre() + "', '" +
                            movie.getOperator().getName() + "', '" +
                            movie.getOperator().getBirthday() + "', '" +
                            movie.getOperator().getWeight() + "', '" +
                            movie.getOperator().getPassportID() + "', '" +
                            movie.getOperator().getHairColor() +
                            "');";
            if (!exception) {
                try {
                    statement.execute(insert_into_MOVIES);
                } catch (SQLException e) {
                    exception = true;
                    message = "Не удалось сохранить коллекцию в базу данных...\n" +
                            e.getMessage();
                }
            }
        }
    }
}
