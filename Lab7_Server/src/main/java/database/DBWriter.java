package database;

import entities.Movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class DBWriter {
    private Connection connection;
    private Statement statement;
    private PasswordProtector passwordProtector;

    public DBWriter (DBConnector dbConnector, PasswordProtector passwordProtector) {
        this.connection = dbConnector.getConnection();
        this.passwordProtector = passwordProtector;
    }

    public boolean clearDBEntitiesTable() {
        final String delete = "DELETE FROM movies;";
        try {
            statement = connection.createStatement();
            statement.execute(delete);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean insertEntityIntoDB(Movie movie, String username) {
        String insert =
                "INSERT INTO movies VALUES (" +
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
                    movie.getOperator().getHairColor() + "', '" +
                        username +
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
                "UPDATE movies " +
                        "SET " +
                        "name = '" + movie.getName() + "', " +
                        "coordinate_x = '" + movie.getCoordinates().getX() + "', " +
                        "coordinate_y = '" + movie.getCoordinates().getY() + "', " +
                        "creation_date = '" + movie.getCreationDate() + "', " +
                        "oscars_count = '" + movie.getOscarsCount() + "', " +
                        "golden_palm_count = '" + movie.getGoldenPalmCount() + "', " +
                        "tagline = '" + movie.getTagline() + "', " +
                        "genre = '" + movie.getGenre() + "', " +
                        "person_name = '" + movie.getOperator().getName() + "', " +
                        "person_birthday = '" + movie.getOperator().getBirthday() + "', " +
                        "person_weight = '" + movie.getOperator().getWeight() + "', " +
                        "person_passport_id = '" + movie.getOperator().getPassportID() + "', " +
                        "person_hair_color = '" + movie.getOperator().getHairColor() + "' " +
                        "WHERE " +
                        "id = '" + id + "';";
        try {
            statement = connection.createStatement();
            statement.execute(update);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteEntityFromDB(Integer id) {
        String delete = "DELETE FROM movies WHERE id = '"+ id +"';";
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

    public String registerNewUser(String username, String password) {
        String select = "SELECT * FROM users WHERE username = '" + username + "';";
        String insert = "INSERT INTO users VALUES ('" + username + "', '" + passwordProtector.createMD5(password) + "');";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select);
            if (rs.next())
                return "Имя пользователя '" + username + "' уже занято.";
            statement.execute(insert);
            return "Регистрация прошла успешно!";
        } catch (SQLException e) {
            return "Внутренняя ошибка сервера... Повторите попытку позднее.";
        }
    }

    public Set<Integer> composeUserIdsToDelete(String username) {
        Set<Integer> idsToDelete = new HashSet<>();
        String select = "SELECT * FROM movies WHERE username = '" + username + "';";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                idsToDelete.add(Integer.parseInt(rs.getString("id")));
            }
            return idsToDelete;
        } catch (SQLException e) {
            return null;
        }
    }
}