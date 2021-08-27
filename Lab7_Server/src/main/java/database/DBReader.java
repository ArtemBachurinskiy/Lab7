package database;

import collection.CollectionManager;
import entities.*;
import output.OutputManager;
import response.Response;

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
    private final String validateUserOK = "Успешный вход в систему!";

    public DBReader (DBConnector dbConnector, CollectionManager collectionManager, OutputManager outputManager) {
        this.connection = dbConnector.getConnection();
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    public void readDBEntitiesTable() {
        String select = "SELECT * FROM movies;";
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось сформировать SQL запрос к базе данных...");
        }
        try {
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                Movie movie = parseRsToMovie(rs);
                if (movie != null)
                    collectionManager.insertMovie(movie.getName(), movie);
            }
        } catch (SQLException e) {
            outputManager.printlnErrorMessage("Не удалось прочитать содержимое таблицы базы данных...");
        }
    }

    public String validateUser(String username, String password) {
        if (username == null || password == null)
            return "Пользователь не авторизован. Введите 'login' - для входа, 'register' - для регистрации.";
        String select = "SELECT * FROM users WHERE username = '" + username + "';";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(select);
            if (rs.next()) {
                if (password.equals(rs.getString("password")))
                    return validateUserOK;
                return "Имя пользователя или пароль неверны.";
            }
            return "Пользователь не найден.";
        } catch (SQLException e) {
            return "Внутренняя ошибка сервера... Повторите попытку позднее.";
        }
    }

    public Movie getMovieWithMaxId() {
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM movies WHERE id = (SELECT MAX(id) FROM movies);");
            if (rs.next()) {
                return parseRsToMovie(rs);
            } else {
                return null;
            }
        } catch (SQLException e){
            return null;
        }
    }

    public boolean userIsDenied(int id, String username) {
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM movies WHERE id = '" + id + "';");
            if (rs.next())
                return !username.equals(rs.getString("username"));
            return true;
        } catch (SQLException e){
            return true;
        }
    }

//    public Movie getMovieById(int id) {
//        try {
//            statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM movies WHERE id = '" + id + "';");
//            if (rs.next()) {
//                return parseRsToMovie(rs);
//            } else {
//                return null;
//            }
//        } catch (SQLException e){
//            return null;
//        }
//    }

    private Movie parseRsToMovie(ResultSet rs) {
        try {
            Integer id = Integer.parseInt(rs.getString("id"));
            String name = rs.getString("name");
            Integer x = Integer.parseInt(rs.getString("coordinate_x"));
            Double y = Double.parseDouble(rs.getString("coordinate_y"));
            LocalDateTime creationDate = LocalDateTime.parse(rs.getString("creation_date"));
            int oscarsCount = Integer.parseInt(rs.getString("oscars_count"));
            long goldenPalmCount = Long.parseLong(rs.getString("golden_palm_count"));
            String tagline = rs.getString("tagline");
            MovieGenre genre = MovieGenre.valueOf(rs.getString("genre"));
            String operator_name = rs.getString("person_name");
            ZonedDateTime birthday = ZonedDateTime.parse(rs.getString("person_birthday"));
            Integer weight = Integer.parseInt(rs.getString("person_weight"));
            String passportID = rs.getString("person_passport_id");
            Color hairColor = Color.valueOf(rs.getString("person_hair_color"));

            Person operator = new Person(operator_name, birthday, weight, passportID, hairColor);
            return new Movie(id, name, new Coordinates(x,y), creationDate, oscarsCount, goldenPalmCount, tagline, genre, operator);
        }
        catch (SQLException e) {
            return null;
        }
    }

    public String getValidateUserOK() {
        return validateUserOK;
    }
}
