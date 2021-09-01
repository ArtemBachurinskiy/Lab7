package input;

import entities.MovieGenre;

import java.io.IOException;

/**
 * Базовый интерфейс для чтения объектов типа Movie.
 */
public interface MovieReader {

    String readName() throws IOException;
    Integer readCoordinateX() throws IOException;
    Double readCoordinateY();
    int readOscarsCount();
    long readGoldenPalmCount();
    String readTagline() throws IOException;
    MovieGenre readGenre();
}
