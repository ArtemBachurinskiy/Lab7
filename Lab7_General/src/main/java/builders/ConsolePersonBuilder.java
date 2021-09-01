package builders;

import entities.Color;
import entities.Person;
import input.PersonReader;

import java.io.IOException;

public class ConsolePersonBuilder implements PersonBuilder {
    private PersonReader personReader;

    public ConsolePersonBuilder(PersonReader personReader) {
        this.personReader = personReader;
    }

    public String buildName() throws IOException {
        return personReader.readPersonName();
    };
    public java.time.ZonedDateTime buildBirthday() {
        return personReader.readBirthday();
    };
    public Integer buildWeight() {
        return personReader.readWeight();
    };
    public String buildPassportID() throws IOException {
        return personReader.readPassportID();
    };
    public Color buildHairColor() {
        return personReader.readHairColor();
    };

    public Person buildPerson() throws IOException {
        return new Person(buildName(), buildBirthday(), buildWeight(), buildPassportID(), buildHairColor());
    }
}
