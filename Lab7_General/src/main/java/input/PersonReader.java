package input;

import entities.Color;
import java.io.IOException;

/**
 * Базовый интерфейс для чтения объектов типа Person.
 */
public interface PersonReader {
    /**
     * @return name
     */
    String readPersonName() throws IOException;
    /**
     * @return birthday
     */
    java.time.ZonedDateTime readBirthday();
    /**
     * @return weight
     */
    Integer readWeight();
    /**
     * @return passportID
     */
    String readPassportID() throws IOException;
    /**
     * @return color
     */
    Color readHairColor();
}
