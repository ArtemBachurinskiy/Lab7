package builders;

import entities.Color;
import entities.Person;

import java.io.IOException;

public interface PersonBuilder {
    String buildName() throws IOException;
    java.time.ZonedDateTime buildBirthday();
    Integer buildWeight();
    String buildPassportID() throws IOException;
    Color buildHairColor();

    Person buildPerson() throws IOException;
}
