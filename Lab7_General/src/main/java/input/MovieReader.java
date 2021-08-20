package input;

import entities.MovieGenre;

import java.io.IOException;

/**
 * Базовый интерфейс для чтения объектов типа Movie.
 */
public interface MovieReader {
    /**
     * @return coordinate x
     */
    Integer readCoordinateX() throws IOException;

    /**
     * @return coordinate y
     */
    Double readCoordinateY();

    /**
     * @return oscarsCount
     */
    int readOscarsCount();

    /**
     * @return goldenPalmCount
     */
    long readGoldenPalmCount();

    /**
     * @return tagline
     */
    String readTagline() throws IOException;

    /**
     * @return genre
     */
    MovieGenre readGenre();
}
