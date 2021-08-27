package request;

import entities.Movie;

import java.io.Serializable;

/**
 * Класс, который представляет собой запрос, отправляемый серверу.
 */
public class Request implements Serializable {
    private String command;
    private String argument;
    private Movie movie;
    private String username;
    private String password;

    public Request(String command, String argument, Movie movie, String username, String password) {
        this.command = command;
        this.argument = argument;
        this.movie = movie;
        this.username = username;
        this.password = password;
    }

    public Request(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
    public String getArgument() {
        return argument;
    }
    public Movie getMovie() {
        return movie;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}