package builders;

import entities.Color;
import entities.Person;
import input.PersonReader;

import java.io.IOException;

/**
 * Класс, который строит объект типа Movie из консоли.
 */
public class ConsolePersonBuilder implements PersonBuilder {
    private PersonReader personReader;

    /**
     * @param personReader Объект класса PersonReader, предназначенный для постройки объекта типа Person из консоли
     */
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

    /**
     * Метод, создающий новый объект типа Person.
     * @return новый объект типа Person
     */
    public Person buildPerson() throws IOException {
        return new Person(buildName(), buildBirthday(), buildWeight(), buildPassportID(), buildHairColor());
    }
}
