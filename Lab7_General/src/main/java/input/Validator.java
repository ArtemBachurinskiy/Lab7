package input;

import output.OutputManager;

/**
 * Класс, предназначенный для валидации полей Movie и Person.
 */
public class Validator {
    private OutputManager outputManager;

    public Validator (OutputManager outputManager) {
        this.outputManager = outputManager;
    }

    /**
     * @param x coordinate x
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validateCoordinateX (Integer x) {
        if (x > -268)
           return true;
        outputManager.printlnErrorMessage("Значение координаты х должно быть больше -268! Повторите ввод.");
        return false;
    }

    /**
     * @param y coordinate y
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validateCoordinateY (Double y) {
        if (y <= 477)
            return true;
        outputManager.printlnErrorMessage("Значение координаты y должно быть меньше или равным 477! Повторите ввод.");
        return false;
    }

    /**
     * @param oscarsCount int oscarsCount
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validateOscarsCount (int oscarsCount) {
        if (oscarsCount > 0)
            return true;
        outputManager.printlnErrorMessage("Значение поля oscarsCount должно быть больше 0! Повторите ввод.");
        return false;
    }

    /**
     * @param goldenPalmCount long goldenPalmCount
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validateGoldenPalmCount (long goldenPalmCount) {
        if (goldenPalmCount > 0)
            return true;
        outputManager.printlnErrorMessage("Значение поля goldenPalmCount должно быть больше 0! Повторите ввод.");
        return false;
    }

    /**
     * @param tagline String tagline
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validateTagline (String tagline) {
        if (tagline.length() <= 214)
            return true;
        outputManager.printlnErrorMessage("Длина строки tagline не должна быть больше 214! Повторите ввод.");
        return false;
    }

    /**
     * @param name String name
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validatePersonName (String name) {
        name = name.trim();
        if (!name.isEmpty())
            return true;
        outputManager.printlnErrorMessage("Поле name не может быть пустой строкой или пробелом! Повторите ввод.");
        return false;
    }

    /**
     * @param weight Integer weight
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validateWeight (Integer weight) {
        if (weight > 0)
            return true;
        outputManager.printlnErrorMessage("Значение поля weight должно быть больше 0! Повторите ввод.");
        return false;
    }

    /**
     * @param passportID String passportID
     * @return true если значение является валидным,
     *         false в противном случае
     */
    boolean validatePassportID (String passportID) {
        passportID = passportID.trim();
        if (passportID.isEmpty()) {
            outputManager.printlnErrorMessage("Поле passportID не может быть пустым! Повторите ввод.");
            return false;
        }
        else if (passportID.length() <= 30)
            return true;
        outputManager.printlnErrorMessage("Длина строки passportID не должна быть больше 30! Повторите ввод.");
        return false;
    }
}
