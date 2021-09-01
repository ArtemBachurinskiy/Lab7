package input;

import entities.Color;
import java.io.IOException;

/**
 * Базовый интерфейс для чтения объектов типа Person.
 */
public interface PersonReader {
    String readPersonName() throws IOException;
    java.time.ZonedDateTime readBirthday();
    Integer readWeight();
    String readPassportID() throws IOException;
    Color readHairColor();
}
