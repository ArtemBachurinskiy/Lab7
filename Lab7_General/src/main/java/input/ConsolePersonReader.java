package input;

import input.InputManager;
import output.OutputManager;
import entities.Color;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Класс, предназначенный для чтения полей Person из консоли.
 */
public class ConsolePersonReader implements PersonReader {
    private InputManager inputManager;
    private OutputManager outputManager;
    private Validator validator;

    public ConsolePersonReader(InputManager inputManager, OutputManager outputManager, Validator validator) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
        this.validator = validator;
    }

    @Override
    public String readPersonName() throws IOException {
        String name;
        outputManager.printlnMessage("Введите поле name (оператора): ");
        do {
            name = inputManager.readLine().trim();
        } while (!validator.validatePersonName(name));
        return name;
    }

    @Override
    public ZonedDateTime readBirthday() {
        ZonedDateTime birthday;
        outputManager.printlnMessage("Введите поле birthday (оператора) в формате \"yyyy-MM-dd\": ");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            try {
                LocalDate localDate = LocalDate.parse(inputManager.readLine().trim(), dateTimeFormatter);
                birthday = ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.of("Europe/Paris"));
                break;
            }
            catch (DateTimeParseException | IOException e) {
                outputManager.printlnErrorMessage("Формат даты: \"yyyy-MM-dd\". Повторите ввод.");
            }
        }
        return birthday;
    }

    @Override
    public Integer readWeight() {
        Integer weight;
        outputManager.printlnMessage("Введите поле weight (оператора): ");
        while (true) {
            try {
                weight = Integer.parseInt(inputManager.readLine().trim());
                if (validator.validateWeight(weight))
                    break;
            }
            catch (NumberFormatException | IOException e) {
                outputManager.printlnErrorMessage("Поле weight должно быть значением типа Integer! Повторите ввод.");
            }
        }
        return weight;
    }

    @Override
    public String readPassportID() throws IOException {
        String passportID;
        outputManager.printlnMessage("Введите поле passportID (оператора): ");
        do {
            passportID = inputManager.readLine().trim();
        } while (!validator.validatePassportID(passportID));
        return passportID;
    }

    @Override
    public Color readHairColor() {
        Color hairColor;
        outputManager.printlnMessage("Введите поле hairColor (оператора): " + '\n' +
                "(возможные варианты ввода: YELLOW, ORANGE, WHITE)");
        while (true) {
            try {
                hairColor = Color.valueOf(inputManager.readLine().trim());
                break;
            } catch (IllegalArgumentException | IOException e) {
                outputManager.printlnErrorMessage("Возможные варианты ввода: YELLOW, ORANGE, WHITE. Повторите ввод.");
            }
        }
        return hairColor;
    }
}
