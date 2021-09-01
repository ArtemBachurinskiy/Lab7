package builders;

import entities.Coordinates;
import entities.Movie;
import entities.MovieGenre;

import java.io.IOException;

public interface MovieBuilder {
    String buildName() throws IOException;
    Coordinates buildCoordinates() throws IOException;
    Integer buildCoordinateX() throws IOException;
    Double buildCoordinateY();
    java.time.LocalDateTime buildCreationDate();
    int buildOscarsCount();
    long buildGoldenPalmCount();
    String buildTagline() throws IOException;
    MovieGenre buildGenre();

    Movie buildMovie(int id, String name) throws IOException;
    Movie buildMovie(int id) throws IOException;
}
