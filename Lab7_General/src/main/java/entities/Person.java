package entities;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Класс Person (дан в тексте лабораторной №5)
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private ZonedDateTime birthday; //Поле не может быть null
    private Integer weight; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Длина строки не должна быть больше 30, Поле не может быть null
    private Color hairColor; //Поле не может быть null

    public Person(String name, ZonedDateTime birthday, Integer weight, String passportID, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
        this.hairColor = hairColor;
    }

    public String getName() {
        return name;
    }
    public ZonedDateTime getBirthday() {
        return birthday;
    }
    public Integer getWeight() {
        return weight;
    }
    public String getPassportID() {
        return passportID;
    }
    public Color getHairColor() {
        return hairColor;
    }

    @Override
    public String toString() {
        return "Person [ name : " + name +
                ", birthday : " + birthday.toLocalDate() +
                ", weight : " + weight +
                ", passportID : " + passportID +
                ", hairColor : " + hairColor + " ]";
    }
}