package builders;

import entities.Coordinates;
import entities.Movie;
import entities.MovieGenre;

import java.io.IOException;

public interface MovieBuilder {
    Coordinates buildCoordinates() throws IOException;
    Integer buildCoordinateX() throws IOException;
    Double buildCoordinateY();
    java.time.LocalDateTime buildCreationDate();
    int buildOscarsCount();
    long buildGoldenPalmCount();
    String buildTagline() throws IOException;
    MovieGenre buildGenre();
    /**
     * @param id поле id объекта Movie.
     * @param name поле name объекта Movie.
     * @return новый объект типа Movie
     */
    Movie buildMovie(int id, String name) throws IOException;
}
