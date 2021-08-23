package database;

import entities.Movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class DBWriter {
    private Connection connection;
    private Statement statement;

    public DBWriter (DBConnector dbConnector) {
        this.connection = dbConnector.getConnection();
    }

    public boolean clearDBEntitiesTable() {
        final String delete = "DELETE FROM MOVIES;";
        try {
            statement = connection.createStatement();
            statement.execute(delete);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insertEntityIntoDB(Movie movie) {
        String insert =
                "INSERT INTO MOVIES VALUES (" +
                    "nextval('id_sequence'), '" +
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
        try {
            statement = connection.createStatement();
            statement.execute(insert);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateEntityOfDB(Integer id, Movie movie) {
        String update =
                "UPDATE MOVIES " +
                        "SET " +
                        "NAME = '" + movie.getName() + "', " +
                        "COORDINATE_X = '" + movie.getCoordinates().getX() + "', " +
                        "COORDINATE_Y = '" + movie.getCoordinates().getY() + "', " +
                        "CREATION_DATE = '" + movie.getCreationDate() + "', " +
                        "OSCARS_COUNT = '" + movie.getOscarsCount() + "', " +
                        "GOLDEN_PALM_COUNT = '" + movie.getGoldenPalmCount() + "', " +
                        "TAGLINE = '" + movie.getTagline() + "', " +
                        "GENRE = '" + movie.getGenre() + "', " +
                        "PERSON_NAME = '" + movie.getOperator().getName() + "', " +
                        "PERSON_BIRTHDAY = '" + movie.getOperator().getBirthday() + "', " +
                        "PERSON_WEIGHT = '" + movie.getOperator().getWeight() + "', " +
                        "PERSON_PASSPORT_ID = '" + movie.getOperator().getPassportID() + "', " +
                        "PERSON_HAIR_COLOR = '" + movie.getOperator().getHairColor() + "' " +
                        "WHERE " +
                        "ID = '" + id + "';";
        try {
            statement = connection.createStatement();
            statement.execute(update);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteEntityFromDB(Integer id) {
        String delete = "DELETE FROM MOVIES WHERE ID = '"+ id +"';";
        try {
            statement = connection.createStatement();
            statement.execute(delete);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void deleteMultipleEntitiesFromDB(Set<Integer> ids) {
        for (Integer id : ids)
            deleteEntityFromDB(id);
    }
}