package builders;

import entities.Coordinates;
import entities.Movie;
import entities.MovieGenre;
import input.MovieReader;

import java.io.IOException;
import java.time.LocalDateTime;

public class ConsoleMovieBuilder implements MovieBuilder {
    private MovieReader movieReader;
    private PersonBuilder personBuilder;

    public ConsoleMovieBuilder(MovieReader movieReader, PersonBuilder personBuilder) {
        this.movieReader = movieReader;
        this.personBuilder = personBuilder;
    }

    @Override
    public String buildName() throws IOException {
        return movieReader.readName();
    }
    @Override
    public Coordinates buildCoordinates() throws IOException {
        return new Coordinates(buildCoordinateX(), buildCoordinateY());
    }
    @Override
    public Integer buildCoordinateX() throws IOException {
        return movieReader.readCoordinateX();
    }
    @Override
    public Double buildCoordinateY() {
        return movieReader.readCoordinateY();
    }
    @Override
    public LocalDateTime buildCreationDate() {
        return LocalDateTime.now();
    }
    @Override
    public int buildOscarsCount() {
        return movieReader.readOscarsCount();
    }
    @Override
    public long buildGoldenPalmCount() {
        return movieReader.readGoldenPalmCount();
    }
    @Override
    public String buildTagline() throws IOException {
        return movieReader.readTagline();
    }
    @Override
    public MovieGenre buildGenre() {
        return movieReader.readGenre();
    }

    @Override
    public Movie buildMovie(int id, String name) throws IOException {
        return new Movie(id, name, buildCoordinates(), buildCreationDate(), buildOscarsCount(),
                buildGoldenPalmCount(), buildTagline(), buildGenre(), personBuilder.buildPerson());
    }
    @Override
    public Movie buildMovie(int id) throws IOException {
        return new Movie(id, buildName(), buildCoordinates(), buildCreationDate(), buildOscarsCount(),
                buildGoldenPalmCount(), buildTagline(), buildGenre(), personBuilder.buildPerson());
    }
}